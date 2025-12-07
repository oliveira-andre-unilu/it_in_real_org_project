import React, { useEffect } from 'react';
import { View, ActivityIndicator, StyleSheet } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

// @ts-ignore
const AuthLoadingScreen = ({ navigation }) => {
    useEffect(() => {
        const checkLogin = async () => {
            const token = await AsyncStorage.getItem('authToken');
            if (token) {
                navigation.replace('Dashboard');
            } else {
                navigation.replace('Login');
            }
        };

        checkLogin();
    }, []);

    return (
        <View style={styles.container}>
            <ActivityIndicator size="large" />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, justifyContent: 'center', alignItems: 'center' },
});

export default AuthLoadingScreen;
