import React, { useState } from 'react';
import { Building2, Users, FolderKanban, Server, ArrowUpRight, Plus, LoaderCircle } from 'lucide-react';
import { api, authStore } from '../api';
import AlertMessage from '../components/common/AlertMessage';

function AuthScreen({onAuthenticated}){
  const [mode,setMode]=useState('login'),[busy,setBusy]=useState(false),[error,setError]=useState(''),[registered,setRegistered]=useState('');
  async function submit(e){
    e.preventDefault();setBusy(true);setError('');setRegistered('');
    const f=new FormData(e.currentTarget);
    try{
      if(mode==='register'){
        await api.register({name:String(f.get('name')||'').trim(),email:String(f.get('email')||'').trim(),password:String(f.get('password')||'')});
        setRegistered('Account created successfully. Sign in with your new account.');setMode('login');
      }else{
        const result=await api.login({email:String(f.get('email')||'').trim(),password:String(f.get('password')||'')});
        authStore.save(result);onAuthenticated(authStore.user());
      }
    }catch(ex){setError(ex.message)}finally{setBusy(false)}
  }
  return <div className="authpage"><section className="authvisual"><div className="authbrand"><span className="brandmark"><Building2 size={24}/></span><span>company<strong>space</strong></span></div><div className="authcopy"><div className="eyebrow">COMPANY MANAGEMENT PLATFORM</div><h1>One workspace for your people, departments and projects.</h1><p>A responsive React dashboard connected through your API Gateway to Employee, Department, Project and Auth microservices.</p><div className="authfeatures"><span><Users size={18}/> Employee management</span><span><Building2 size={18}/> Department management</span><span><FolderKanban size={18}/> Projects & assignments</span><span><Server size={18}/> JWT-ready microservice integration</span></div></div><small>React · Spring Boot · Eureka · API Gateway · MySQL</small></section><section className="authpanel"><div className="authcard"><div className="mobileauthbrand"><Building2 size={22}/> companyspace</div><div className="eyebrow">{mode==='login'?'WELCOME BACK':'CREATE ACCOUNT'}</div><h2>{mode==='login'?'Sign in to your workspace':'Register a new user'}</h2><p>{mode==='login'?'Use your Auth Service credentials to continue.':'Registration creates a user in auth_db through the Auth Service.'}</p><AlertMessage type="error" message={error}/><AlertMessage type="success" message={registered}/><form className="authform" onSubmit={submit}>{mode==='register'&&<label className="field"><span>Full name</span><input name="name" required minLength="2" placeholder="Enter your name" autoComplete="name"/></label>}<label className="field"><span>Email address</span><input name="email" type="email" required placeholder="you@example.com" autoComplete="email"/></label><label className="field"><span>Password</span><input name="password" type="password" required minLength={mode==='register'?8:1} placeholder="Enter your password" autoComplete={mode==='login'?'current-password':'new-password'}/></label><button className="primary authsubmit" disabled={busy}>{busy?<LoaderCircle className="spin" size={18}/>:mode==='login'?<ArrowUpRight size={18}/>:<Plus size={18}/>} {busy?'Please wait...':mode==='login'?'Sign in':'Create account'}</button></form><div className="authswitch">{mode==='login'?"Don't have an account? ":'Already registered? '}<button onClick={()=>{setMode(mode==='login'?'register':'login');setError('');setRegistered('')}}>{mode==='login'?'Create one':'Sign in'}</button></div></div></section></div>
}

export default AuthScreen;
