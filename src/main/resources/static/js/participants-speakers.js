new Vue({
    el: '#app',
    data: {
        participantSpeakers: [],
        events: [],
        newParticipantSpeaker: {
            name: '',
            email: '',
            company: '',
            role: '',
            status: '',
            eventParticipation: '',
            relationshipWithCompany: '',
            bio: '',
            multipleRoles: ''
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
            axios.get('/api/events')
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
        addParticipantSpeaker() {
            axios.post('/participants-speakers', this.newParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers.push({ ...this.newParticipantSpeaker, id: response.data.data.id });
                        this.newParticipantSpeaker = { name: '', email: '', company: '', role: '', status: '', eventParticipation: '', relationshipWithCompany: '', bio: '', multipleRoles: '' };
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
        },
        updateParticipantSpeaker() {
            axios.put(`/participants-speakers/${this.currentParticipantSpeaker.id}`, this.currentParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        const index = this.participantSpeakers.findIndex(ps => ps.id === this.currentParticipantSpeaker.id);
                        this.$set(this.participantSpeakers, index, this.currentParticipantSpeaker);
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
            alert(`Published ${participantSpeaker.name}`);
        },
        cancelEdit() {
            this.editMode = false;
            this.currentParticipantSpeaker = {};
        }
    }
});
