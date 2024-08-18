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
            eventDateTime: '' // Add this to store the selected date value
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
            axios.get(`/api/events/${this.newParticipantSpeaker.eventId}/schedules`)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.schedules = response.data.data;
                    } else {
                        alert('Failed to fetch event schedules');
                    }
                })
                .catch(error => {
                    console.error('Failed to fetch event schedules:', error);
                });
        },
        addParticipantSpeaker() {
            const selectedEvent = this.events.find(event => event.id === this.newParticipantSpeaker.eventId);
            const selectedSchedule = this.schedules.find(schedule => schedule.id === this.newParticipantSpeaker.scheduleId);

            if (!selectedEvent || !selectedSchedule) {
                alert('Please select a valid event and schedule.');
                return;
            }

            const eventDateTime = selectedSchedule.date; // Only the date value is selected here

            const newParticipantSpeaker = {
                ...this.newParticipantSpeaker,
                eventName: selectedEvent.title,
                eventDateTime: eventDateTime // Use only the selected date value
            };

            axios.post('/participants-speakers', newParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers.push({
                            ...newParticipantSpeaker,
                            id: response.data.data.id,
                            eventName: selectedEvent.title,
                            eventDateTime: eventDateTime,
                            relationshipWithCompany: this.newParticipantSpeaker.relationshipWithCompany,
                            bio: this.newParticipantSpeaker.bio
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
            this.fetchSchedules(); // Populate schedules when editing
        },
        updateParticipantSpeaker() {
            const selectedEvent = this.events.find(event => event.id === this.currentParticipantSpeaker.eventId);
            const selectedSchedule = this.schedules.find(schedule => schedule.id === this.currentParticipantSpeaker.scheduleId);

            if (!selectedEvent || !selectedSchedule) {
                alert('Please select a valid event and schedule.');
                return;
            }

            const eventDateTime = selectedSchedule.date; // Only the date value is selected here

            const updatedParticipantSpeaker = {
                ...this.currentParticipantSpeaker,
                eventName: selectedEvent.title,
                eventDateTime: eventDateTime, // Use only the selected date value
                relationshipWithCompany: this.currentParticipantSpeaker.relationshipWithCompany,
                bio: this.currentParticipantSpeaker.bio
            };

            axios.put(`/participants-speakers/${this.currentParticipantSpeaker.id}`, updatedParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        const index = this.participantSpeakers.findIndex(ps => ps.id === this.currentParticipantSpeaker.id);
                        this.$set(this.participantSpeakers, index, updatedParticipantSpeaker);
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
                this.fetchSchedules(); // Fetch schedules when event changes
            }
        }
    }
});
