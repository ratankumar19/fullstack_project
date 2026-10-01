# Company Management React UI

Responsive React/Vite frontend integrated with the project's API Gateway and microservices.

## Covered backend functions
- Auth Service: register, login, current-user JWT support
- Employee Service: list, create, update, delete
- Department Service: list, create, update, delete
- Project Service: list, create, update, delete, project details
- Project Assignment endpoints: list by project, assign employee, remove assignment
- API Gateway health status
- Responsive dashboard, search, validation, loading/error/success states and mobile navigation

## Run
1. Start MySQL.
2. Start `config-server` (8888).
3. Start `discovery-server` (8761).
4. Start Auth Service, Employee Service, Department Service and Project Service.
5. Start `api-gateway` (8080).
6. In this folder run:
   `npm install`
   `npm run dev`
7. Open `http://localhost:5173`.

Vite proxies `/api` and `/actuator` to `http://localhost:8080` by default.

## Important
The API Gateway application.properties in this package includes an `auth-service` route so login/register can be reached through port 8080.
The UI stores the access token in browser localStorage and sends it as `Authorization: Bearer <token>` on subsequent API calls.

## Refactored frontend architecture

The UI is now split by responsibility so the application can grow without keeping the entire product in `main.jsx`.

```
src/
├── App.jsx                     # authentication/workspace routing boundary
├── main.jsx                    # React bootstrap only
├── api.js                      # API Gateway client + auth storage
├── config/
│   └── resources.js            # resource metadata and project statuses
├── utils/
│   └── formatters.js           # display formatting helpers
├── components/
│   └── common/
│       └── AlertMessage.jsx    # reusable feedback component
└── pages/
    ├── AuthScreen.jsx          # login/register experience
    └── WorkspaceApp.jsx        # authenticated management workspace
```

This refactor also removes the unsafe `e.currentTarget.reset()` call after an awaited registration request. React can clear/change the form through state/rendering instead, avoiding the `Cannot read properties of null (reading 'reset')` error seen after registration.

### Recommended next scaling step

As each domain grows, move Employee, Department, Project, and Assignment UI into `features/<domain>/` folders and move Workspace state/data orchestration into dedicated hooks. The current structure creates those boundaries without changing the backend API contract.
