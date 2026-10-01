export function dateText(value) {
  return value ? new Date(`${value}T00:00:00`).toLocaleDateString('en-IN', { day:'2-digit', month:'short', year:'numeric' }) : '—';
}

export function money(value) {
  return value == null ? '—' : `₹${Number(value).toLocaleString('en-IN')}`;
}
