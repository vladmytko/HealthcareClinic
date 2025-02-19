<h1>Health Care Clinic Application</h1>

<ul>Technologies Used</ul>
<li>Java</li>
<li>Spring Boot</li>
<li>React</li>
<li>MySQL</li>
<li>AWS S3</li>


<h2>Initial commit with authentication and functionality for users, staff, and patients.</h2>

<ul>Working:</ul>
<li>User authentication</li>
<li>Staff functionality</li>
<li>Patient functionality</li>
<li>Task functionality</li>

<ul>Pending</ul>
<li>Image functionality</li>
<li>AWS S3 integration</li>

# Healthcare Clinic API Documentation

## Task Completion Status

Below is the current status of API endpoints, indicating which tasks have been completed, need updates, or have failed.

### **Authentication (`/auth`)**
| Task      | Role  | Result     | Comment |
|-----------|-------|------------|---------|
| **/register** | - | ✅ Completed | Staff can be registered only by admin. For patients, it is public. |
| **/login** | - | ✅ Completed |  |

### **User Management (`/users`)**
| Task | Role | Result |
|------|------|---------|
| **/get-all-users** | Admin | ✅ Completed |
| **/get-user-by-id/user ID** | Admin | ✅ Completed |
| **/delete-user/user ID** | Admin | ✅ Completed |
| **/get-user-info/user ID** | Admin | ✅ Completed |

### **Staff Management (`/staff`)**
| Task | Role | Result |
|------|------|---------|
| **/add-staff** | Admin | ✅ Completed |
| **/get-staff-by-id/staff ID** | Admin | ✅ Completed |
| **/get-staff-by-email/email** | Admin | ✅ Completed |
| **/get-staff-by-name/firstName/lastName** | Admin | ✅ Completed |
| **/get-all-staff** | Admin | ✅ Completed |
| **/get-staff-info-by-id/Staff ID** | Admin | ✅ Completed |
| **/get-staff-info-by-email/email** | Admin | ✅ Completed |
| **/get-logged-in-staff-info/email** | Staff | ✅ Completed |
| **/delete-staff/staff ID** | Admin | ✅ Completed |
| **/update-staff/staff ID** | Admin/Staff | ✅ Completed |

### **Patient Management (`/patient`)**
| Task | Role | Result |
|------|------|---------|
| **/add-patient** | Admin/Staff | ✅ Completed |
| **/get-patient-by-id/ID** | Admin/Staff | ✅ Completed |
| **/get-patient-by-email/email** | Admin/Staff | ✅ Completed |
| **/get-patient-by-name/firstName/lastName** | Admin/Staff | ✅ Completed |
| **/get-all-patients** | Admin/Staff | ✅ Completed |
| **/get-logged-in-patient-info/email** | Patient | ✅ Completed |
| **/delete-patient-by-id/patientID** | All | ✅ Completed |
| **/update-patient/ID** | Admin/Staff | ✅ Completed |
| **/update-patient-diagnosis/patient ID** | Admin/Staff | ✅ Completed |

### **Task Management (`/task`)**
| Task | Role | Result | Comment |
|------|------|---------|---------|
| **/add-task** | Admin/Staff | ❌ Needs Update | When creating a task, need to enter manually staff ID and patient ID. But for patient, it should enter patient ID automatically. |
| **/get-task-by-id/task ID** | Admin | ✅ Completed | |
| **/get-all-tasks** | Admin | ✅ Completed | |
| **/get-task-by-staff-id/staff ID** | Admin | ✅ Completed | |
| **/get-task-by-patient-id/patient ID** | Admin | ✅ Completed | |
| **/update-task/task ID** | Admin/Staff | ✅ Completed | |
| **/completion-status/task ID** | Admin/Staff | ✅ Completed | |

### **Image Management (`/image`)**
| Task | Role | Result |
|------|------|---------|
| **/add-image** | Admin/Staff | ❌ Fail |
| **/get-image-by-id/image ID** | Admin/Staff | ❌ Fail |
| **/get-all-images** | Admin/Staff | ❌ Fail |
| **/get-images-by-patient-id/patient ID** | Admin/Staff | ❌ Fail |
| **/delete-image-by-id/image ID** | Admin/Staff | ❌ Fail |
| **/update-image-by-id/image ID** | Admin/Staff | ❌ Fail |

---

### **Legend**
✅ **Completed** → Fully functional  
❌ **Needs Update** → Requires changes  
❌ **Fail** → Not working  

---

### **Next Steps**
- Fix `/task/add-task` to automatically set patient ID when a patient creates a task.
- Debug `/image/` related APIs to fix failures.
- Optimize API response messages for better clarity.

---

