import { AlertCircle, CheckCircle2 } from 'lucide-react';

export default function AlertMessage({ type = 'error', message }) {
  if (!message) return null;
  const Icon = type === 'success' ? CheckCircle2 : AlertCircle;
  return <div className={`alert ${type}`}><Icon size={17}/>{message}</div>;
}
