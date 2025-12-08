// React Native
import React, { useEffect, useState } from 'react';
import {
    View,
    Text,
    StyleSheet,
    TouchableOpacity,
    SafeAreaView,
    ScrollView,
    Alert
} from 'react-native';

// Internal Storage
import AsyncStorage from '@react-native-async-storage/async-storage';

// Axios
import axios from 'axios';

// GPS Functionality
import * as Location from 'expo-location';

// Images
import Icon from 'react-native-vector-icons/Ionicons';
import { Image } from 'expo-image';

// Import External Scripts
import { getProjects, postTimestamp } from './apiClient';


// @ts-ignore
const Dashboard = ({ navigation }) => {

    const [projects, setProjects] = useState<any[]>([]);
    const [selectedProjectId, setSelectedProjectId] = useState<any | null>(null); // Not sure using any here is a good idea
    const [userLocation, setUserLocation] = useState<any | null>(null); // Not sure using any here is a good idea
    const [currentShift, setCurrentShift] = useState<any | null>(null); // Not sure using any here is a good idea
    const [timeTick, setTimeTick] = useState(0);


      ////////////////////////////
     // On Page Load Functions //
    ////////////////////////////

    // Updates TimeTick
    useEffect(() => {
        const interval = setInterval(() => {
            setTimeTick(t => t + 1); // forces re-render
        }, 60000); // every 60 seconds

        return () => clearInterval(interval);
    }, []);


    // Check if there is an ongoing shift
    useEffect(() => {
        const loadShift = async () => {
            const storedShift = await AsyncStorage.getItem("currentShift");
            if (storedShift) {
                setCurrentShift(JSON.parse(storedShift));
            }
        };
        loadShift();
    }, []);


    // Fetch user's GPS location
    useEffect(() => {
        const requestLocation = async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                Alert.alert("Error", "Location permission denied.");
                return;
            }

            let location = await Location.getCurrentPositionAsync({});
            setUserLocation({
                latitude: location.coords.latitude,
                longitude: location.coords.longitude
            });
        };

        requestLocation();
    }, []);


    // Fetch Work Sites (API placeholder)
    // Replace with real backend API later
    useEffect(() => {
        const fetchSites = async () => {
            try {
                   ///////////////////////////////////////////////////////////////
                  //     IMPORTANT IMPORTANT IMPORTANT IMPORTANT IMPORTANT     //
                 // Original code (works with proper back-end implementation) //
                ///////////////////////////////////////////////////////////////
                // const data = await getProjects();
                // setProjects(data);



                   ///////////////////////////////////////////////////////////////
                  //     IMPORTANT IMPORTANT IMPORTANT IMPORTANT IMPORTANT     //
                 // Temporary Fix for improper coordinate values in back-end  //
                ///////////////////////////////////////////////////////////////                
                const raw = await getProjects();

                // FIX: Convert "location" string into latitude & longitude
                const cleaned = raw.map((project: any) => {
                    if (project.location && typeof project.location === "string") {
                        const [latStr, lonStr] = project.location.split(";");

                        const latitude = parseFloat(latStr);
                        const longitude = parseFloat(lonStr);

                        return {
                            ...project,
                            latitude: isNaN(latitude) ? null : latitude,
                            longitude: isNaN(longitude) ? null : longitude
                        };
                    }

                    // If location is missing, fallback safely
                    return {
                        ...project,
                        latitude: project.latitude ?? null,
                        longitude: project.longitude ?? null
                    };
                });

                setProjects(cleaned);
            } catch (err) {
                Alert.alert("Error", "Could not load projects.\n" + err);
            }

        };

        fetchSites();
    }, []);




      //////////////////////
     // Helper Functions //
    //////////////////////

    // Calculate distance between user <-> work site
    const calculateDistance = (lat1: number, lon1: number, lat2: number, lon2: number) => {
        if (!lat1 || !lon1 || !lat2 || !lon2) return null;

        const R = 6371000; // Earth radius in meters
        const toRad = (v: number) => (v * Math.PI) / 180;

        const dLat = toRad(lat2 - lat1);
        const dLon = toRad(lon2 - lon1);

        const a =
            Math.sin(dLat / 2) ** 2 +
            Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
            Math.sin(dLon / 2) ** 2;

        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        const distance = R * c;

        if (distance < 1000) {
            return `${Math.round(distance)} m`;
        } else {
            return `${(distance / 1000).toFixed(2)} km`;
        }
    };


    // Get Time Since start of Shift
    const getTimeAgo = (timestamp: Date) => {
        const start: any = new Date(timestamp);
        const now: any = new Date();

        const diffMs = now - start;
        const diffMin = Math.floor(diffMs / 60000);
        const diffH = Math.floor(diffMin / 60);
        const mins = diffMin % 60;

        if (diffH > 0) return `${diffH} h ${mins} m ago`;
        return `${mins} m ago`;
    };




      ////////////////////
     // Main Functions //
    ////////////////////

    // Start Shift Function
    const handleStartShift = async () => {
        if (!selectedProjectId) {
            Alert.alert("No selection", "Please select a work site first.");
            return;
        }

        const selectedProject = projects.find(s => s.id === selectedProjectId);

        let startTime = new Date().toISOString();

        let entry: any = {
            startTime: startTime,
            //Duration added on End Shift Handle
            latitude: userLocation?.latitude || null,
            longitude: userLocation?.longitude || null,
            // employeeId: , // CHECK HOW TO IMPLEMENT THIS
            projectId: selectedProject.id,
            projectName: selectedProject.name,
            tag: "Work",
        };

        await AsyncStorage.setItem("currentShift", JSON.stringify(entry));

        Alert.alert("Shift Started", `Shift started at: ${startTime}`);

        setCurrentShift(entry);
    };


    // End Shift Function
    const handleEndShift = async () => {
        const shiftData = await AsyncStorage.getItem("currentShift");

        if (!shiftData) return;

        // TODO: send to backend

        await AsyncStorage.removeItem("currentShift");

        setCurrentShift(null);
    };



    return (
        <SafeAreaView style={styles.container}>
            {/* App Logo */}
            <View style={styles.header}>
                <Image
                    source={require('../assets/images/timelink_lightMode_lowProfile.png')}
                    style={styles.logo}
                    contentFit="contain"
                />
            </View>

            {/* Main Content */}
            <View style={styles.content}>
                {!currentShift ? (
                    <>
                        {/* Select Shift View */}

                        <Text style={styles.subtitle}>Select a Shift</Text>
                    
                        {/* Scrollable list */}
                        <ScrollView style={{ width: "100%" }}>
                            {projects.map((project) => {

                                const distanceText = userLocation
                                    ? calculateDistance(
                                        userLocation.latitude,
                                        userLocation.longitude,
                                        project.latitude,
                                        project.longitude
                                    )
                                    : "Locating...";

                                return (
                                    <TouchableOpacity
                                        key={project.id}
                                        style={[
                                            styles.card,
                                            selectedProjectId === project.id && styles.cardSelected
                                            ]}
                                        onPress={() => setSelectedProjectId(project.id)} >
                                        {/* Left Section: Project Name & Number */}
                                        <View style={styles.cardLeft}>
                                            <Text style={styles.projectName}>{project.name}</Text>
                                            <Text style={styles.projectNumber}>{project.number}</Text>
                                        </View>

                                        {/* Right Section: GPS Logo & Distance */}
                                        <View style={styles.cardRight}>
                                            <Icon name="location-outline" size={22} color="#444" />
                                            <Text style={styles.distanceText}>{distanceText}</Text>
                                        </View>
                                    </TouchableOpacity>
                                );
                            })}
                        </ScrollView>

                        {/* Start Shift Button */}
                        <TouchableOpacity style={styles.startButton} onPress={handleStartShift}>
                            <Text style={styles.startButtonText}>Start Shift</Text>
                        </TouchableOpacity>
                    </>
                ) : (
                    <>
                        {/* Current Shift View */}

                        <Text style={styles.subtitle}>Current Shift</Text>

                        <View style={styles.runningShiftContainer}>
                            <Text style={styles.runningText}>
                                Start: {new Date(currentShift.startTime).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })} ({getTimeAgo(currentShift.startTime)})
                            </Text>

                            {/* Use timeTick to trigger re-render */}
                            {timeTick > -1 && null}

                            <Text style={styles.runningText}>
                                Location: {currentShift.projectName}
                            </Text>
                        </View>

                        {/* End Shift Button */}
                        <TouchableOpacity style={styles.endButton} onPress={handleEndShift}>
                            <Text style={styles.endButtonText}>End Shift</Text>
                        </TouchableOpacity>
                    </>
                )}
            </View>


            {/* Bottom Navigation */}
            <View style={styles.navbar}>
                <TouchableOpacity style={styles.navItem} onPress={() => navigation.navigate('Monitor')}>
                    <Icon name="stats-chart-sharp" size={28} color="#333" />
                    <Text style={styles.navText}>Monitor</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.navItem} onPress={() => navigation.navigate('Dashboard')}>
                    <Icon name="speedometer-outline" size={28} color="#333" />
                    <Text style={styles.navText}>Dashboard</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.navItem} onPress={() => navigation.navigate('Settings')}>
                    <Icon name="settings-outline" size={28} color="#333" />
                    <Text style={styles.navText}>Settings</Text>
                </TouchableOpacity>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    // Global Styles
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    header: {
        paddingTop: 20,
        paddingBottom: 20,
        alignItems: 'center',
        // backgroundColor: '#f8f8f8',
    },
    logo: {
        // backgroundColor: 'darkgrey',
        // borderRadius: 300,
        width: 200,
        height: 100,
        textAlign: "center",
    },
    content: {
        flex: 1,
        // justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 20,
    },
    subtitle: {
        fontSize: 24,
        fontWeight: '600',
        marginBottom: 10,
        color: '#555',
    },
    description: {
        fontSize: 16,
        textAlign: 'center',
        color: '#777',
    },
    navbar: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        paddingVertical: 15,
        borderTopWidth: 1,
        borderTopColor: '#eee',
        backgroundColor: '#fafafa',
    },
    navItem: {
        alignItems: 'center',
    },
    navText: {
        fontSize: 12,
        color: '#333',
        marginTop: 4,
    },


    // Select Shift Styles
    card: {
        width: "100%",
        backgroundColor: "#f9f9f9",
        padding: 15,
        borderRadius: 12,
        marginBottom: 12,
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        borderWidth: 2,
        borderColor: "transparent"
    },
    cardSelected: {
        borderColor: "#007AFF",
    },
    cardLeft: {
        width: "80%",
    },
    projectName: {
        fontSize: 18,
        fontWeight: "700",
        color: "#333",
    },
    projectNumber: {
        fontSize: 14,
        color: "#666",
        marginTop: 3,
    },
    cardRight: {
        alignItems: "center",
        justifyContent: "center",
        width: "20%",
    },
    distanceText: {
        marginTop: 4,
        color: "#444",
        fontSize: 14,
    },
    startButton: {
        width: "100%",
        backgroundColor: "#007AFF",
        paddingVertical: 14,
        borderRadius: 10,
        marginTop: 15,
    },
    startButtonText: {
        textAlign: "center",
        color: "#fff",
        fontSize: 18,
        fontWeight: "600",
    },


    // Current Shift Styles
    runningShiftContainer: {
        width: "100%",
        borderWidth: 2,
        padding: 15,
        borderRadius: 12,
        marginBottom: 12,
        backgroundColor: "#f9f9f9",
    },
    runningText: {
        fontSize: 18,
        // fontWeight: "700",
    },
    endButton: {
        width: "100%",
        backgroundColor: "#c00404ff",
        paddingVertical: 14,
        borderRadius: 10,
        marginTop: 15,
    },
    endButtonText: {
        textAlign: "center",
        color: "#fff",
        fontSize: 18,
        fontWeight: "600",
    },
});

export default Dashboard;
