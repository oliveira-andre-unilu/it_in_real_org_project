import React, { useEffect, useRef, useState } from 'react';
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    SafeAreaView,
    Alert,
    BackHandler
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

// @ts-ignore
const LoginScreen = ({ navigation }) => {
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

    // const handleLogin = async () => {
    //     // TODO: Replace with backend API call later
    //     if (email === '' || password === '') {
    //         Alert.alert('Error', 'Please fill in both fields.');
    //         return;
    //     }

    //     // Fake authentication for now
    //     if (email.toLowerCase() === 'test@test.com' && password === '1234') {
    //         await AsyncStorage.setItem('authToken', 'FAKE_TOKEN_ABC123');
    //         navigation.replace('Dashboard');
    //     } else {
    //         Alert.alert('Login failed', 'Invalid email or password.');
    //     }
    // };

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

            // Alert.alert("Token", token);
            // Alert.alert("serverLocation", serverLocation);

        } catch (err) {
            console.error(err);
            Alert.alert("Failed", "Invalid login credentials.");
        }
    }

    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Login</Text>

            <TextInput
                ref={serverLocationRef}
                style={styles.input}
                placeholder="Server Url"
                keyboardType="url"
                onChangeText={setServerLocation}
                value={serverLocation}
                onSubmitEditing={() => inputEmailRef.current?.focus()}
                blurOnSubmit={false}
            />

            <TextInput
                ref={inputEmailRef}
                style={styles.input}
                placeholder="Email"
                keyboardType="email-address"
                onChangeText={setEmail}
                value={email}
                onSubmitEditing={() => inputPasswordRef.current?.focus()}
                blurOnSubmit={false}
            />

            <TextInput
                ref={inputPasswordRef}
                style={styles.input}
                placeholder="Password"
                secureTextEntry
                onChangeText={setPassword}
                value={password}
                onSubmitEditing={handleLogin}
            />

            <TouchableOpacity   style={styles.button} 
                                onPress={handleLogin}>
                <Text style={styles.buttonText}>Log In</Text>
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
