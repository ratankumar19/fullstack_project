export const META = {
  employees: {
    title: 'Employees', single: 'Employee',
    fields: [['name','Full name','text',true],['email','Email address','email',true],['designation','Designation','text',true],['salary','Annual salary','number',true],['departmentId','Department','department',true]]
  },
  departments: {
    title: 'Departments', single: 'Department',
    fields: [['name','Department name','text',true],['code','Department code','text',true],['description','Description','textarea',false]]
  },
  projects: {
    title: 'Projects', single: 'Project',
    fields: [['name','Project name','text',true],['code','Project code','text',true],['description','Description','textarea',false],['status','Status','status',true],['startDate','Start date','date',true],['endDate','End date','date',false],['departmentId','Department','department',true]]
  }
};

export const STATUSES = ['PLANNED','ACTIVE','ON_HOLD','COMPLETED','CANCELLED'];
