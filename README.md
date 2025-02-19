# Health Care Clinic Application

## Technologies Used
- **Java** (Backend)
- **Spring Boot** (API & Business Logic)
- **React** (Frontend)
- **MySQL** (Database)
- **AWS S3** (Storage for images)

## Project Overview
This application provides an online healthcare clinic management system with authentication and role-based access control for **users, staff, and patients**.

### ✅ **Currently Implemented Features**
- **User Authentication** (Login, Registration)
- **Staff Management** (CRUD operations, role management)
- **Patient Management** (CRUD operations, patient records)
- **Task Management** (Assigning tasks to staff and patients)

### ⏳ **Pending Features**
- **Image Management** (Uploading, fetching, updating, and deleting images)
- **AWS S3 Integration** (To store and retrieve images securely)

### **Next Steps**
- Fix `/task/add-task` to automatically set patient ID when a patient creates a task.
- Debug `/image/` related APIs to fix failures.
- Optimize API response messages for better clarity.
- Integrate AWS S3 for secure image storage.
- Enhance security & validation for all user actions.

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
| **/add-task** | Admin/Staff | ⚠️ Needs Update | When creating a task, need to enter manually staff ID and patient ID. But for patient, it should enter patient ID automatically. |
| **/get-task-by-id/task ID** | Admin | ✅ Completed | |
| **/get-all-tasks** | Admin | ✅ Completed | |
| **/get-task-by-staff-id/staff ID** | Admin | ✅ Completed | |
| **/get-task-by-patient-id/patient ID** | Admin | ✅ Completed | |
| **/update-task/task ID** | Admin/Staff | ✅ Completed | |
| **/completion-status/task ID** | Admin/Staff | ✅ Completed | |

### **Image Management (`/image`)**
| Task | Role | Result |
|------|------|---------|
| **/add-image** | Admin/Staff | ✅ Completed |
| **/get-image-by-id/image ID** | Admin/Staff | ✅ Completed |
| **/get-all-images** | Admin/Staff | ✅ Completed |
| **/get-images-by-patient-id/patient ID** | Admin/Staff | ✅ Completed |
| **/delete-image-by-id/image ID** | Admin/Staff | ⚠️ Deletes image entity from mySQL, does not delete image from S3 |
| **/update-image-by-id/image ID** | Admin/Staff | ⚠️ Partially works, image does not update |

---

### **Legend**
✅ **Completed** → Fully functional  
❌ **Fail** → Not working  
⚠️ **Error** → Requires changes 
---



