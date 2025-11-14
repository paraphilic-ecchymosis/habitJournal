import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, ScrollView, ActivityIndicator } from 'react-native';
import { habitAPI, entryAPI, milestoneAPI } from '../services/api';

export default function DashboardScreen() {
  const [stats, setStats] = useState({
    totalHabits: 0,
    totalEntries: 0,
    totalMilestones: 0,
    completedMilestones: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [habitsRes, entriesRes, milestonesRes] = await Promise.all([
        habitAPI.getAll(),
        entryAPI.getAll(),
        milestoneAPI.getAll(),
      ]);

      const habits = habitsRes.data;
      const entries = entriesRes.data;
      const milestones = milestonesRes.data;

      setStats({
        totalHabits: habits.length,
        totalEntries: entries.length,
        totalMilestones: milestones.length,
        completedMilestones: milestones.filter(m => m.status === 'COMPLETED').length,
      });
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <View style={styles.centerContainer}>
        <ActivityIndicator size="large" color="#0ea5e9" />
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.title}>Dashboard</Text>

      <View style={styles.statsGrid}>
        <View style={[styles.statCard, { backgroundColor: '#3b82f6' }]}>
          <Text style={styles.statValue}>{stats.totalHabits}</Text>
          <Text style={styles.statLabel}>Total Habits</Text>
        </View>

        <View style={[styles.statCard, { backgroundColor: '#10b981' }]}>
          <Text style={styles.statValue}>{stats.totalEntries}</Text>
          <Text style={styles.statLabel}>Total Entries</Text>
        </View>

        <View style={[styles.statCard, { backgroundColor: '#f59e0b' }]}>
          <Text style={styles.statValue}>{stats.totalMilestones}</Text>
          <Text style={styles.statLabel}>Total Milestones</Text>
        </View>

        <View style={[styles.statCard, { backgroundColor: '#8b5cf6' }]}>
          <Text style={styles.statValue}>{stats.completedMilestones}</Text>
          <Text style={styles.statLabel}>Completed Milestones</Text>
        </View>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  centerContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 20,
    color: '#1f2937',
  },
  statsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
  },
  statCard: {
    width: '48%',
    padding: 20,
    borderRadius: 12,
    marginBottom: 15,
    alignItems: 'center',
  },
  statValue: {
    fontSize: 32,
    fontWeight: 'bold',
    color: 'white',
    marginBottom: 5,
  },
  statLabel: {
    fontSize: 14,
    color: 'white',
    textAlign: 'center',
  },
});
