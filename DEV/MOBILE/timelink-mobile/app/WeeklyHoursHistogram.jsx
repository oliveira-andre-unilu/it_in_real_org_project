import React, { useState, useMemo } from 'react';
import { View, Text, Button, ButtonGroup, StyleSheet, TouchableOpacity } from 'react-native';
import moment from 'moment';
import { BarChart } from 'react-native-gifted-charts';

export default function WeeklyHoursHistogram({ data }) {
  const [weekOffset, setWeekOffset] = useState(0);

  // Get Monday and Sunday of the displayed week
  const weekStart = useMemo(() => moment().startOf('week').add(weekOffset, 'weeks'), [weekOffset]);
  const weekEnd = useMemo(() => moment().endOf('week').add(weekOffset, 'weeks'), [weekOffset]);

  // Convert API data → hours per day map
  const hoursPerDay = useMemo(() => {
    const result = { Mon: 0, Tue: 0, Wed: 0, Thu: 0, Fri: 0, Sat: 0, Sun: 0 };

    data.forEach(entry => {
      if (entry.tag !== "WORK") return;

      const date = moment(entry.startingTime);

      if (date.isBetween(weekStart, weekEnd, null, '[]')) {
        const weekday = date.format('ddd'); // Mon, Tue, Wed, ...
        result[weekday] = (result[weekday] || 0) + entry.duration;
      }
    });

    return result;
  }, [data, weekStart, weekEnd]);

  // Convert to BarChart format
  const chartData = useMemo(() => {
    return [
      { label: 'Mon', value: hoursPerDay.Mon },
      { label: 'Tue', value: hoursPerDay.Tue },
      { label: 'Wed', value: hoursPerDay.Wed },
      { label: 'Thu', value: hoursPerDay.Thu },
      { label: 'Fri', value: hoursPerDay.Fri },
      { label: 'Sat', value: hoursPerDay.Sat },
      { label: 'Sun', value: hoursPerDay.Sun },
    ];
  }, [hoursPerDay]);

  return (
    <View style={{ padding: 16, width:"95%", alignItems:'center', textAlign:'center'}}>
      <Text style={{ fontSize: 20, marginBottom: 10, textAlign: 'center' }}>
        Week of {weekStart.format("YYYY-MM-DD")}
      </Text>

      <BarChart
        data={chartData}
        barWidth={30}
        spacing={10}
        roundedTop
        noOfSections={4}
        maxValue={8}   // Adjust based on expected hours
        yAxisTextStyle={{ color: '#444' }}
        xAxisLabelTextStyle={{ color: '#444' }}
      />

      <View style={{ flexDirection: 'row', justifyContent: 'space-evenly', marginTop: 20}}>
          {/* <Button title="Previous Week" onPress={() => setWeekOffset(weekOffset - 1)}/>
          <Button title="Next Week" onPress={() => setWeekOffset(weekOffset + 1)} /> */}
          <TouchableOpacity style={styles.startButton} onPress={() => setWeekOffset(weekOffset - 1)}>
              <Text style={styles.startButtonText}>Previous week</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.startButton} onPress={() => setWeekOffset(weekOffset + 1)}>
              <Text style={styles.startButtonText}>Next week</Text>
          </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
    startButton: {
        width: "50%",
        backgroundColor: "#007AFF",
        paddingVertical: 14,
        borderRadius: 10,
        marginTop: 15,
        marginLeft:10,
        marginRight:10
    },
    startButtonText: {
        textAlign: "center",
        color: "#fff",
        fontSize: 18,
        fontWeight: "600",
    },
});
