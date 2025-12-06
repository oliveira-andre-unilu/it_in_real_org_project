import { Image } from 'expo-image';
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
import Icon from 'react-native-vector-icons/Ionicons';
import * as Location from 'expo-location';
import AsyncStorage from '@react-native-async-storage/async-storage';

// @ts-ignore
const Dashboard = ({ navigation }) => {

    const [projects, setProjects] = useState([]);
    const [selectedProjectId, setSelectedProjectId] = useState(null);
    const [userLocation, setUserLocation] = useState(null);

    // ---------------------------
    // Fetch user's GPS location
    // ---------------------------
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


    // --------------------------------------
    // Fetch Work Sites (API placeholder)
    // Replace with real backend API later
    // --------------------------------------
    useEffect(() => {
        const fetchSites = async () => {
            // Sample data (replace with backend fetch)
            const sampleData = [
                { id: 1, name: "Belval Uni", number: "A12", latitude: 49.504575, longitude: 5.949298 },
                { id: 2, name: "Luxembourg Gare", number: "B57", latitude: 49.600764, longitude: 6.134055 },
                { id: 3, name: "André's Home", number: "HQ-01", latitude: 49.486582, longitude: 6.086660 },
                { id: 4, name: "Test Location 1", number: "TEST01", latitude: 49.487582, longitude: 6.186660 },
                { id: 5, name: "Test Location 2", number: "TEST02", latitude: 49.488582, longitude: 6.176660 },
                { id: 6, name: "Test Location 3", number: "TEST03", latitude: 49.488482, longitude: 6.177660 }
            ];
            setProjects(sampleData);
        };

        fetchSites();
    }, []);


    // ------------------------------------------------
    // Calculate distance between user <-> work site
    // ------------------------------------------------
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


    // ----------------------------
    // Start Shift Saving Function
    // ----------------------------
    const handleStartShift = async () => {
        if (!selectedProjectId) {
            Alert.alert("No selection", "Please select a work site first.");
            return;
        }

        const selectedProject = projects.find(s => s.id === selectedProjectId);

        let startTime = new Date().toISOString();

        let entry = {
            projectId: selectedProject.id,
            tag: "Work",
            startTime: startTime,
            latitude: userLocation?.latitude || null,
            longitude: userLocation?.longitude || null
        };

        await AsyncStorage.setItem("currentShift", JSON.stringify(entry));

        Alert.alert("Shift Started", `Shift started at: ${startTime}`);
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
                <Text style={styles.subtitle}>Select a Shift:</Text>
                
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
                                {/* Left Section */}
                                <View style={styles.cardLeft}>
                                    <Text style={styles.projectName}>{project.name}</Text>
                                    <Text style={styles.projectNumber}>{project.number}</Text>
                                </View>

                                {/* Right Section */}
                                <View style={styles.cardRight}>
                                    {/* Temporary — replace this with your GPS icon */}
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
        justifyContent: 'center',
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

    /* Cards */
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
    siteName: {
        fontSize: 18,
        fontWeight: "700",
        color: "#333",
    },
    siteNumber: {
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

    /* Start Shift Button */
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
});

export default Dashboard;
