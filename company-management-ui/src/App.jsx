import React, { useState } from 'react';
import { authStore } from './api';
import AuthScreen from './pages/AuthScreen';
import WorkspaceApp from './pages/WorkspaceApp';

export default function App() {
  const [user, setUser] = useState(() => authStore.user());
  const logout = () => { authStore.clear(); setUser(null); };
  return user
    ? <WorkspaceApp user={user} onLogout={logout}/>
    : <AuthScreen onAuthenticated={setUser}/>;
}
