import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

// Create axios instance (baseURL added later)
const api = axios.create({
    timeout: 10000,
});

// INTERCEPTOR: attach jwtToken from AsyncStorage to every request
api.interceptors.request.use(async (config) => {

    // Load base URL dynamically
    const serverLocation = await AsyncStorage.getItem("serverLocation");
    if (serverLocation) {
        config.baseURL = serverLocation;
        // console.log("REQUEST TO:", config.baseURL + config.url);
    }

    // Load token dynamically
    const token = await AsyncStorage.getItem("authToken");
    if (token) {
        config.headers.jwtToken = token;
        // console.log("HEADER TOKEN:", config.headers.jwtToken);
    }

    return config;
});

export default api;


// GET /api/projects/
export const getProjects = async () => {
    try {
        const response = await api.get("/api/projects");
        return response.data;
    } catch (err) {
        // console.log("SERVER LOCATION:", await AsyncStorage.getItem("serverLocation"));
        console.error("Error getting projects:", err);
        throw err;
    }
};


// POST /api/timestamps/
export const postTimestamp = async (shiftData: any) => {
    try {
        const response = await api.post("/api/timestamps", shiftData);
        return response.data;
    } catch (err) {
        console.error("Error posting timestamp:", err);
        throw err;
    }
};
