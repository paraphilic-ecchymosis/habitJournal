import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import { milestoneAPI } from '../services/api';

export default function MilestonesScreen() {
  const [milestones, setMilestones] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchMilestones();
  }, []);

  const fetchMilestones = async () => {
    try {
      const response = await milestoneAPI.getAll();
      setMilestones(response.data);
    } catch (error) {
      console.error('Error fetching milestones:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'COMPLETED': return '#10b981';
      case 'IN_PROGRESS': return '#3b82f6';
      case 'FAILED': return '#ef4444';
      case 'CANCELLED': return '#f59e0b';
      default: return '#6b7280';
    }
  };

  const renderMilestone = ({ item }) => (
    <View style={styles.milestoneCard}>
      <Text style={styles.milestoneName}>{item.name}</Text>
      <Text style={styles.milestoneDescription}>{item.description}</Text>

      <View style={[styles.statusBadge, { backgroundColor: getStatusColor(item.status) }]}>
        <Text style={styles.statusText}>{item.status}</Text>
      </View>

      {item.goalUnits && (
        <View style={styles.progressContainer}>
          <View style={styles.progressBar}>
            <View
              style={[
                styles.progressFill,
                { width: `${((item.completedUnits || 0) / item.goalUnits) * 100}%` }
              ]}
            />
          </View>
          <Text style={styles.progressText}>
            {item.completedUnits || 0} / {item.goalUnits}
          </Text>
        </View>
      )}
    </View>
  );

  if (loading) {
    return (
      <View style={styles.centerContainer}>
        <ActivityIndicator size="large" color="#0ea5e9" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={milestones}
        renderItem={renderMilestone}
        keyExtractor={(item) => item.id}
        ListEmptyComponent={
          <Text style={styles.emptyText}>No milestones yet. Create your first milestone!</Text>
        }
      />
    </View>
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
  milestoneCard: {
    backgroundColor: 'white',
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  milestoneName: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  milestoneDescription: {
    fontSize: 14,
    color: '#666',
    marginBottom: 10,
  },
  statusBadge: {
    alignSelf: 'flex-start',
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 12,
    marginBottom: 10,
  },
  statusText: {
    color: 'white',
    fontSize: 12,
    fontWeight: 'bold',
  },
  progressContainer: {
    marginTop: 5,
  },
  progressBar: {
    height: 8,
    backgroundColor: '#e5e7eb',
    borderRadius: 4,
    overflow: 'hidden',
    marginBottom: 5,
  },
  progressFill: {
    height: '100%',
    backgroundColor: '#0ea5e9',
  },
  progressText: {
    fontSize: 12,
    color: '#666',
  },
  emptyText: {
    textAlign: 'center',
    marginTop: 50,
    fontSize: 16,
    color: '#666',
  },
});
