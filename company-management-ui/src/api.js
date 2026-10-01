const ROOT = (import.meta.env.VITE_API_BASE_URL || '').replace(/\/$/, '');
const TOKEN_KEY = 'company_management_access_token';
const USER_KEY = 'company_management_user';

export const authStore = {
  token: () => localStorage.getItem(TOKEN_KEY),
  user: () => { try { return JSON.parse(localStorage.getItem(USER_KEY) || 'null'); } catch { return null; } },
  save: (payload) => {
    if (payload?.accessToken) localStorage.setItem(TOKEN_KEY, payload.accessToken);
    localStorage.setItem(USER_KEY, JSON.stringify({ userId: payload.userId, name: payload.name, email: payload.email, role: payload.role }));
  },
  clear: () => { localStorage.removeItem(TOKEN_KEY); localStorage.removeItem(USER_KEY); }
};

export async function request(path, options = {}) {
  let response;
  const token = authStore.token();
  const headers = {
    ...(options.body ? { 'Content-Type': 'application/json' } : {}),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers
  };
  try { response = await fetch(`${ROOT}${path}`, { ...options, headers }); }
  catch { throw new Error('Cannot connect to API Gateway. Check that port 8080 is running.'); }
  if (response.status === 204) return null;
  const raw = await response.text(); let data;
  try { data = raw ? JSON.parse(raw) : null; } catch { data = raw; }
  if (!response.ok) {
    if (response.status === 401 && !path.includes('/api/auth/login')) authStore.clear();
    const fields = data?.errors || data?.validationErrors;
    const detail = fields && typeof fields === 'object' ? Object.entries(fields).map(([k,v]) => `${k}: ${v}`).join(' · ') : '';
    throw new Error(detail || data?.message || data?.detail || `${response.status} ${response.statusText}`);
  }
  return data;
}

export const api = {
  login: (body) => request('/api/auth/login', { method:'POST', body:JSON.stringify(body) }),
  register: (body) => request('/api/auth/register', { method:'POST', body:JSON.stringify(body) }),
  me: () => request('/api/auth/me'),
  authPing: () => request('/api/auth/ping'),
  list: (resource) => request(`/api/${resource}`),
  create: (resource, body) => request(`/api/${resource}`, { method:'POST', body:JSON.stringify(body) }),
  update: (resource, id, body) => request(`/api/${resource}/${id}`, { method:'PUT', body:JSON.stringify(body) }),
  remove: (resource, id) => request(`/api/${resource}/${id}`, { method:'DELETE' }),
  details: (id) => request(`/api/projects/${id}/details`),
  assignments: (id) => request(`/api/project-assignments/project/${id}`),
  employeeAssignments: (id) => request(`/api/project-assignments/employee/${id}`),
  assign: (employeeId, projectId) => request('/api/project-assignments', { method:'POST', body:JSON.stringify({ employeeId:Number(employeeId), projectId:Number(projectId) }) }),
  unassign: (employeeId, projectId) => request(`/api/project-assignments/employee/${employeeId}/project/${projectId}`, { method:'DELETE' }),
  gatewayHealth: () => request('/actuator/health')
};
