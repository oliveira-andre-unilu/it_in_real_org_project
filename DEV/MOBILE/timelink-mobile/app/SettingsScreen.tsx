import React, { useState } from 'react';
import Icon from 'react-native-vector-icons/Ionicons';
import {
    View,
    Text,
    TouchableOpacity,
    StyleSheet,
    SafeAreaView,
    Alert
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

// @ts-ignore
const SettingsScreen = ({ navigation }) => {
    const [isDark, setIsDark] = useState(false);

    const toggleTheme = () => {
        setIsDark(!isDark);
        Alert.alert("Theme changed", `Theme switched to ${!isDark ? "Dark" : "Light"} Mode`);
    };

    const handleLogout = async () => {
        Alert.alert(
            "Logout",
            "Are you sure you want to log out?",
            [
                {
                    text: "Cancel",
                    style: "cancel"
                },
                {
                    text: "Log out",
                    style: "destructive",
                    onPress: async () => {
                        await AsyncStorage.removeItem('authToken');
                        navigation.replace('Login');
                    }
                }
            ]
        );
    };

    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Settings</Text>

            {/* Main Content */}
            <View style={styles.content}>
                {/* Toggle Theme Button */}
                <TouchableOpacity style={styles.button} onPress={toggleTheme}>
                    <Text style={styles.buttonText}>
                        Switch to {isDark ? "Light" : "Dark"} Mode (WIP)
                    </Text>
                </TouchableOpacity>

                {/* Logout Button */}
                <TouchableOpacity
                    style={[styles.button, { backgroundColor: '#d9534f' }]}
                    onPress={handleLogout}>
                    <Text style={styles.buttonText}>Log Out</Text>
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
        justifyContent: 'center',
        paddingHorizontal: 30,
    },
    title: {
        fontSize: 32,
        fontWeight: '600',
        textAlign: 'center',
        marginBottom: 40,
    },
    content: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 20,
    },
    input: {
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 10,
        padding: 12,
        marginVertical: 10,
        fontSize: 16,
    },
    button: {
        marginTop: 20,
        backgroundColor: '#9e9e9eff',
        padding: 15,
        borderRadius: 10,
    },
    buttonText: {
        color: '#fff',
        textAlign: 'center',
        fontWeight: '600',
        fontSize: 16,
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

export default SettingsScreen;
