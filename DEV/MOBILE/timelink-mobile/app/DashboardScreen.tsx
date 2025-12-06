import { Image } from 'expo-image';
import React from 'react';
import {
    View,
    Text,
    StyleSheet,
    TouchableOpacity,
    SafeAreaView
} from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

// @ts-ignore
const Dashboard = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            {/* App Logo */}
            <View style={styles.header}>
                <Image
                    source={require('../assets/images/timelink.png')}
                    style={styles.logo}
                    contentFit="contain"
                />
            </View>

            {/* Main Content */}
            <View style={styles.content}>
                <Text style={styles.subtitle}>Welcome to Timelink!</Text>
                <Text style={styles.description}>
                    Manage your time efficiently and keep everything under control.
                </Text>
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
        paddingTop: 50,
        paddingBottom: 20,
        alignItems: 'center',
        backgroundColor: '#f8f8f8',
    },
    logo: {
        backgroundColor: 'darkgrey',
        borderRadius: 300,
        width: 250,
        height: 250,
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
