import { createStackNavigator } from '@react-navigation/stack';
import Dashboard from './DashboardScreen';
import LoginScreen from './LoginScreen';
import AuthLoadingScreen from './AuthLoadingScreen';
import SettingsScreen from './SettingsScreen';
import MonitorScreen from './MonitorScreen';
import {ThemeContext} from "@/app/ThemeContext";
import {useContext} from "react";

const Stack = createStackNavigator();

export default function AppNavigator() {
    const { theme } = useContext(ThemeContext);
    const isDark = theme === "dark";

    return (
        <Stack.Navigator
            initialRouteName="AuthLoading"
            screenOptions={{
                headerStyle: {
                    backgroundColor: isDark ? "#000" : "#fff",
                },
                headerTintColor: isDark ? "#fff" : "#000",
                headerTitleStyle: {
                    fontWeight: "bold",
                },
            }}
        >
            <Stack.Screen name="AuthLoading" component={AuthLoadingScreen} />
            <Stack.Screen
                name="Login"
                component={LoginScreen}
                options={{
                    headerShown: false,
                    headerLeft: () => null,
                    gestureEnabled: false,
                }}
            />
            <Stack.Screen
                name="Dashboard"
                component={Dashboard}
                options={{
                    headerLeft: () => null,
                    gestureEnabled: false,
                }}
            />
            <Stack.Screen name="Settings" component={SettingsScreen} />
            <Stack.Screen name="Monitor" component={MonitorScreen} />
        </Stack.Navigator>
    );
}