import axios from "axios";

export default class ApiService {

    static BASE_URL = "http://localhost:4040";

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: token ? `Bearer ${token}`: "", // Ensure token is not null
            "Content-Type": "application/json"
        };
    }

    /**
     * Helper function to validate inputs before making PUT or PATCH requests.
     * Ensures no null or undefined values are sent.
     */

    static validatePayload(payload) {
        if(Object.values(payload).some(value => value === null || value === undefined)) {
            console.error("Validation Error: Some required fields are missing.");
            throw new Error("Validation Error: All required fields must be provided.");
        }
    }

    /** AUTH */

    /* Register new user */
    static async registerUser(registration) {
        try {
            const response = await axios.post(`${this.BASE_URL}/auth/register`, registration);
            return response.data;
        } catch (error) {
            console.error("Registration Error:", error.response?.data || error.message);
            throw error;
        }
    }

    /* Login */
    static async loginUser(loginDetails) {
        try{
            const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails);
            return response.data;
        } catch (error) {
            console.error("Login Error:", error.response?.data || error.message);
            throw error;
        }
    }

    /** USER FUNCTIONS */

    /* Get all users (ADMIN) */
    static async getAllUsers(){
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-all-users`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all users", error.response?.data || error.message);
            throw error;
        }
    }

    /* Get user profile (ADMIN) */
    static async getUserProfile(userId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-info/${userId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user profile", error.response?.data || error.message);
            throw error;
        }
    }

    /* Get user by ID (ADMIN) */
    static async getUserById(userId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/users/get-user-by-id/${userId}`,{
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting user by ID", error.response?.data || error.message );
        }
    }

    /**Functions above do the same thing, may need to remove one of them */

    /* Delete user by ID (ADMIN) */
    static async deleteUser(userId) {
        try{
            const response = await axios.delete(`${this.BASE_URL}/users/delete-user/${userId}`,{
                headers: this.getHeader()
            });
            return response;
        } catch (error) {
            console.error("Error deleteing user by ID", error.response?.data || error.message );
        }
    }


    /** PATIENT FUNCTIONS */

    /* Get Patient by ID (ADMIN and STAFF)*/
    static async getPatient(patientId) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-patient-by-id/${patientId}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting patient by ID", error.response?.data || error.message );
        }
    }

    /* Get Patient by email (ADMIN and STAFF)*/
    static async getPatient(email) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-patient-by-email/${email}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting patient by email", error.response?.data || error.message );
        }
    }

    /* Get Patient by name (ADMIN and STAFF) */
    static async getPatient(firstName, lastName) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-patient-by-name/${firstName}/${lastName}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting patient by name", error.response?.data || error.message );
        }
    }

     /* Get all patients (ADMIN) */
     static async getAllPatients() {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-all-patients`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all patients", error.response?.data || error.message );
        }
    }

    /* Get logged in patient account information (PATIENT) */

    static async getLoggedInPatientAccount() {
        try {
            const response =  await axios.get(`${this.BASE_URL}/get-logged-in-patients-info`,{
                headers: this.getHeader()
            });
            return response.data
        } catch (error) {
            console.error("Error getting logged in patient account", error.response?.data || error.message );
            throw error;
        }
    }

    /* Delete patient using patient ID (ADMIN) */
   
    static async deletePatient(patientId) {
        try {
            const response =  await axios.delete(`${this.BASE_URL}/delete-patient-by-id/${patientId}`,{
                headers: this.getHeader()
            });
            return response.data
        } catch (error) {
            console.error("Error deleteing patient account", error.response?.data || error.message );
        }
    }

    /* Update patient details (ADMIN or STAFF) */
    static async updatePatientDetails(patientId, patientData) {

        this.validatePayload({...patientData, patientId});

        try {
            const response = await axios.patch(`${this.BASE_URL}/update-patient/${patientId}`,
               patientData, // Sending as request body
               {headers: this.getHeader()}
            );
            return response.data;
        } catch {
            console.error("Error updating patient details", error.response?.data || error.message);
            throw error;
        }
    }

    /* Update self patient details (PATIENT) */
    static async updateSelfPatientDetails(patientData) {
        this.validatePayload(patientData);

        try{
            const response = await axios.patch(`${this.BASE_URL}/update-self-patient`,
                patientData,
                {headers:this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error updating self patient details", error.response?.data || error.message);
            throw error;
        }
    }






    /** STAFF FUNCTIONS */

    /* Create staff account (ADMIN) */
    static async createStaffAccount(staffData){
        this.validatePayload(staffData);

        try{
            const response = await axios.post(`${this.BASE_URL}/add-staff`, 
                staffData,
                {headers:this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error creating staff account", error.response?.data || error.message);
            throw error;
        }
    }

    // Get staff account by ID (ADMIN)
    static async getStaffbyId(staffId) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-staff-by-id/${staffId}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting staff by ID", error.response?.data || error.message );
        }
    }

    // Get staff by Email (ADMIN)
    static async getStaffbyEmail(email) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-staff-by-email/${email}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting staff by email", error.response?.data || error.message );
        }
    }
    
    // Get staff by name (ADMIN)
    static async getStaffbyName(firstName, lastName) {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-staff-by-name/${firstName}/${lastName}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting staff by name", error.response?.data || error.message );
        }
    }

    // Get all staff (ADMIN)
    static async getAllStaff() {
        try {
            const response = await axios.get(`${this.BASE_URL}/patient/get-all-staff}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all staff", error.response?.data || error.message );
        }
    }

    // Delete staff account by ID (ADMIN) 
    static async deleteStaff() {
        try {
            const response = await axios.delete(`${this.BASE_URL}/patient/delete-staff/${staffId}}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error deleting staff by ID", error.response?.data || error.message );
        }
    }

    // Update staff details (ADMIN)
    static async updateStaffDetails(staffId, staffData) {

        this.validatePayload({...staffData, staffId});

        try {
            const response = await axios.patch(`${this.BASE_URL}/update-staff/${staffId}`,
               staffData, // Sending as request body
               {headers: this.getHeader()}
            );
            return response.data;
        } catch {
            console.error("Error updating staff details", error.response?.data || error.message);
            throw error;
        }
    }

    /* Update self staff details (STAFF) */
    static async updateSelfPatientDetails(staffData) {
        this.validatePayload(staffData);

        try{
            const response = await axios.patch(`${this.BASE_URL}/update-self-staff`,
                staffData,
                {headers:this.getHeader()}
            );
            return response.data;
        } catch (error) {
            console.error("Error updating self staff details", error.response?.data || error.message);
            throw error;
        }
    }


    /** TASK FUNCTIONS */
    // Create task (ADMIN and STAFF)
    static async addTask(taskData){
        this.validatePayload(taskData);

        try{
            const response = await axios.post(`${this.BASE_URL}/add-task`, taskData, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error adding task", error.response?.data || error.message);
            throw error;
        }
    }

    // Get task by ID (ADMIN)
    static async getTaskByID(taskId){

        try{
            const response = await axios.get(`${this.BASE_URL}/get-task-by-id/${taskId}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting task by ID", error.response?.data || error.message);
            throw error;
        }
    }

    // Get all tasks (ADMIN)
    static async getAllTasks() {
        try{
            const response = await axios.get(`${this.BASE_URL}/get-all-tasks`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting all tasks", error.response?.data || error.message);
            throw error;
        }
    }

    // Get task by staff ID (ADMIN and STAFF)
    static async getTaskByStaffId(staffId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/get-task-by-staff-id/${staffId}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting tasks by staff ID", error.response?.data || error.message);
            throw error;
        }
    }

    // Get task by patient ID 
    // Patient should get tasks automatically
    static async getTaskByPatientId(patientId) {
        try{
            const response = await axios.get(`${this.BASE_URL}/get-task-by-patient-id/${patientId}`, {
                headers: this.getHeader()
            });
            return response.data;
        } catch (error) {
            console.error("Error getting tasks by patient ID", error.response?.data || error.message);
            throw error;
        }
    }

    // Delete task 

}