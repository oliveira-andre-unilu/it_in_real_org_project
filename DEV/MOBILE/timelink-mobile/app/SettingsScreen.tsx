import React, { useState } from 'react';
import {
    View,
    Text,
    TextInput,
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
        await AsyncStorage.removeItem('authToken');
        navigation.replace('Login');
    };

    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Settings</Text>

            {/* Toggle Theme Button */}
            <TouchableOpacity style={styles.button} onPress={toggleTheme}>
                <Text style={styles.buttonText}>
                    Switch to {isDark ? "Light" : "Dark"} Mode (WIP)
                </Text>
            </TouchableOpacity>

            {/* Logout Button */}
            <TouchableOpacity
                style={[styles.button, { backgroundColor: '#d9534f' }]}
                onPress={handleLogout}
            >
                <Text style={styles.buttonText}>Log Out</Text>
            </TouchableOpacity>
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
});

export default SettingsScreen;
