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

    /* Get all users*/
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

    /* Get user profile */
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

    /* Get user by ID */
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

    /* Delete user by ID */
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

    /* Get Patient by ID */
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

    /* Get Patient by email */
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

    /* Get Patient by name */
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

     /* Get all patients */
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


 


    
}