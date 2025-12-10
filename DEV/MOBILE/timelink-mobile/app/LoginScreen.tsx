import React, { useEffect, useRef, useState, useContext } from 'react';
import { ThemeContext } from './ThemeContext';
import {View, Text, TextInput, TouchableOpacity, StyleSheet, SafeAreaView, Alert, BackHandler} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

// @ts-ignore
const LoginScreen = ({ navigation }) => {
    const { theme } = useContext(ThemeContext);
    const isDark = theme === 'dark';

    const serverLocationRef = useRef(null);
    const inputEmailRef = useRef(null);
    const inputPasswordRef = useRef(null);

    // Handles back swipe gesture
    // Code from: https://reactnavigation.org/docs/preventing-going-back/
    React.useEffect(() => {
        const onBackPress = () => {
            Alert.alert(
                'Exit App',
                'Do you want to exit?',
                [
                    {
                        text: 'Cancel',
                        onPress: () => {
                            // Do nothing
                        },
                        style: 'cancel',
                    },
                    { text: 'YES', onPress: () => BackHandler.exitApp() },
                ],
                { cancelable: false }
            );

            return true;
        };

        const backHandler = BackHandler.addEventListener(
            'hardwareBackPress',
            onBackPress
        );

        return () => backHandler.remove();
    }, []);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [serverLocation, setServerLocation] = useState('');

    const handleLogin = async () => {
        if (!serverLocation || !email || !password) {
            Alert.alert("Error", "Please enter all fields.");
            return;
        }

        try {
            const res = await axios.post(
                `${serverLocation}/api/auth/signin`,
                { email, password },
                { headers: { "Content-Type": "application/json" } }
            );

            const token = res.data;
            await AsyncStorage.setItem("authToken", token);
            await AsyncStorage.setItem("serverLocation", serverLocation);

            navigation.replace("Dashboard");

        } catch (err) {
            console.error(err);
            Alert.alert("Failed", "Invalid login credentials.");
        }
    }

    const styles = themedStyles(isDark);

    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Login</Text>

            <TextInput
                ref={serverLocationRef}
                style={styles.input}
                placeholder="Server Url"
                placeholderTextColor={isDark ? "#aaa" : "#999"} // Dark Mode Placeholder
                keyboardType="url"
                onChangeText={setServerLocation}
                importantForAutofill='yes'
                autoCapitalize='none'
                value={serverLocation}
                onSubmitEditing={() => inputEmailRef.current?.focus()}
                blurOnSubmit={false}
            />

            <TextInput
                ref={inputEmailRef}
                style={styles.input}
                placeholder="Email"
                placeholderTextColor={isDark ? "#aaa" : "#999"} // Dark Mode Placeholder
                keyboardType="email-address"
                onChangeText={setEmail}
                importantForAutofill='yes'
                autoCapitalize='none'
                value={email}
                onSubmitEditing={() => inputPasswordRef.current?.focus()}
                blurOnSubmit={false}
            />

            <TextInput
                ref={inputPasswordRef}
                style={styles.input}
                placeholder="Password"
                placeholderTextColor={isDark ? "#aaa" : "#999"} // Dark Mode Placeholder
                secureTextEntry
                onChangeText={setPassword}
                importantForAutofill='yes'
                autoCapitalize='none'
                value={password}
                onSubmitEditing={handleLogin}
            />

            <TouchableOpacity style={styles.button} onPress={handleLogin}>
                <Text style={styles.buttonText}>Log In</Text>
            </TouchableOpacity>
        </SafeAreaView>
    );
};

const themedStyles = (dark: boolean) =>
    StyleSheet.create({
        container: {
            flex: 1,
            backgroundColor: dark ? "#000" : "#fff", // Dark Mode Background
            paddingHorizontal: 30,
        },
        title: {
            fontSize: 32,
            fontWeight: '600',
            textAlign: 'center',
            marginTop: 125,
            marginBottom: 40,
            color: dark ? "#fff" : "#000", // Dark Mode Text
        },
        input: {
            borderWidth: 1,
            borderColor: dark ? "#555" : "#ccc", // Dark Mode Border
            borderRadius: 10,
            padding: 12,
            marginVertical: 10,
            fontSize: 16,
            color: dark ? "#fff" : "#000", // Dark Mode Text
        },
        button: {
            marginTop: 20,
            backgroundColor: '#007AFF',
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

export default LoginScreen;