new Vue({
    el: '#app',
    data: {
        participantSpeakers: [],
        events: [],
        schedules: [],
        newParticipantSpeaker: {
            name: '',
            email: '',
            company: '',
            role: '',
            status: '',
            eventId: '',
            scheduleId: '',
            relationshipWithCompany: '',
            bio: '',
            eventDateTime: ''
        },
        currentParticipantSpeaker: {},
        editMode: false
    },
    mounted() {
        this.fetchParticipantSpeakers();
        this.fetchEvents();
    },
    methods: {
        fetchParticipantSpeakers() {
            axios.get('/participants-speakers')
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers = response.data.data;
                    } else {
                        alert('Failed to fetch participants and speakers');
                    }
                })
                .catch(error => {
                    console.error('Failed to fetch participants and speakers:', error);
                });
        },
        fetchEvents() {
            axios.get('/participants-speakers/events')
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.events = response.data.data;
                    } else {
                        alert('Failed to fetch events');
                    }
                })
                .catch(error => {
                    console.error('Failed to fetch events:', error);
                });
        },
        fetchSchedules() {
            const eventId = this.newParticipantSpeaker.eventId;
            if (eventId) {
                axios.get(`/api/events/${eventId}/schedules`)
                    .then(response => {
                        if (response.data.status === 'SUCCESS') {
                            this.schedules = response.data.data.map(schedule => {
                                return {
                                    ...schedule,
                                    startTime: schedule.startTime || 'N/A',
                                    endTime: schedule.endTime || 'N/A'
                                };
                            });
                        } else {
                            alert('Failed to fetch event schedules');
                        }
                    })
                    .catch(error => {
                        console.error('Failed to fetch event schedules:', error);
                    });
            } else {
                console.error('No eventId provided to fetch schedules');
            }
        },
        addParticipantSpeaker() {
            const selectedEvent = this.events.find(event => event.id === this.newParticipantSpeaker.eventId);
            const selectedSchedule = this.schedules.find(schedule => schedule.id === this.newParticipantSpeaker.scheduleId);

            if (!selectedEvent || !selectedSchedule) {
                alert('Please select a valid event and schedule.');
                return;
            }

            // Combine the date and time as a string
            const eventDateTime = `${selectedSchedule.date} ${selectedSchedule.startTime} - ${selectedSchedule.endTime}`;

            const newParticipantSpeaker = {
                ...this.newParticipantSpeaker,
                eventName: selectedEvent.title,
                eventDateTime: eventDateTime,
            };

            axios.post('/participants-speakers', newParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers.push({
                            ...newParticipantSpeaker,
                            id: response.data.data.id,
                        });
                        this.resetNewParticipantSpeaker();
                    } else {
                        alert('Failed to add participant/speaker');
                    }
                })
                .catch(error => {
                    console.error('Failed to add participant/speaker:', error);
                });
        },
        editParticipantSpeaker(participantSpeaker) {
            this.currentParticipantSpeaker = { ...participantSpeaker };
            this.editMode = true;

            // Set the eventId and scheduleId to ensure correct fetching of schedules
            this.newParticipantSpeaker.eventId = this.currentParticipantSpeaker.eventId;
            this.newParticipantSpeaker.scheduleId = this.currentParticipantSpeaker.scheduleId;

            console.log('Editing participant speaker:', this.currentParticipantSpeaker);

            // Fetch the schedules only if eventId is available
            if (this.newParticipantSpeaker.eventId) {
                this.fetchSchedules();
            } else {
                console.error('No eventId available for this participant/speaker');
            }
        },
        updateParticipantSpeaker() {
            // Check if currentParticipantSpeaker has a valid ID
            const participantId = this.currentParticipantSpeaker.id;
            if (!participantId) {
                alert('No participant/speaker ID found');
                return;
            }

            const selectedEvent = this.events.find(event => event.id === this.currentParticipantSpeaker.eventId);
            const selectedSchedule = this.schedules.find(schedule => schedule.id === this.currentParticipantSpeaker.scheduleId);

            if (!selectedEvent || !selectedSchedule) {
                alert('Please select a valid event and schedule.');
                return;
            }

            const eventDateTime = `${selectedSchedule.date} ${selectedSchedule.startTime} - ${selectedSchedule.endTime}`;

            const updatedParticipantSpeaker = {
                ...this.currentParticipantSpeaker,
                eventName: selectedEvent.title,
                eventDateTime: eventDateTime,
            };

            console.log('Updating participant speaker with ID:', participantId);

            axios.put(`/participants-speakers/${participantId}`, updatedParticipantSpeaker)
                    .then(response => {
                        if (response.data.status === 'SUCCESS') {
                            this.fetchParticipantSpeakers(); // Re-fetch to ensure data is up-to-date
                            this.editMode = false;
                            this.currentParticipantSpeaker = {};
                        } else {
                            alert('Failed to update participant/speaker');
                        }
                    })
                    .catch(error => {
                        console.error('Failed to update participant/speaker:', error);
                    });
        },
        deleteParticipantSpeaker(id) {
            if (!id) {
                alert('No participant/speaker ID found');
                return;
            }

            axios.delete(`/participants-speakers/${id}`)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers = this.participantSpeakers.filter(ps => ps.id !== id);
                    } else {
                        alert('Failed to delete participant/speaker');
                    }
                })
                .catch(error => {
                    console.error('Failed to delete participant/speaker:', error);
                });
        },
        sendEmail(email) {
            window.location.href = `mailto:${email}`;
        },
        publishParticipantSpeaker(participantSpeaker) {
            axios.post(`/participants-speakers/${participantSpeaker.id}/publish`)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        alert(`Published ${participantSpeaker.name} at ${response.data.data}`);
                    } else {
                        alert('Failed to publish participant/speaker');
                    }
                })
                .catch(error => {
                    console.error('Failed to publish participant/speaker:', error);
                });
        },
        cancelEdit() {
            this.editMode = false;
            this.currentParticipantSpeaker = {};
        },
        resetNewParticipantSpeaker() {
            this.newParticipantSpeaker = {
                name: '',
                email: '',
                company: '',
                role: '',
                status: '',
                eventId: '',
                scheduleId: '',
                relationshipWithCompany: '',
                bio: '',
                eventDateTime: '' // Reset the eventDateTime field as well
            };
        }
    },
    watch: {
        'newParticipantSpeaker.eventId': function(newEventId) {
            if (newEventId) {
                this.fetchSchedules(newEventId); // Fetch schedules when event changes
            }
        }
    }
});
