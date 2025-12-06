import React from 'react';
import Icon from 'react-native-vector-icons/Ionicons';
import {
    View,
    Text,
    TouchableOpacity,
    StyleSheet,
    SafeAreaView,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

// @ts-ignore
const MonitorScreen = ({ navigation }) => {


    return (
        <SafeAreaView style={styles.container}>
            <Text style={styles.title}>Monitor Shifts</Text>

            {/* Main Content */}
            <View style={styles.content}>
                
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
        // justifyContent: 'center',
        // paddingHorizontal: 30,
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

export default MonitorScreen;
