new Vue({
    el: '#app',
    data: {
        participantSpeakers: [],
        newParticipantSpeaker: {
            name: '',
            email: '',
            company: '',
            role: 'Speaker',
            status: 'Invited'
        },
        currentParticipantSpeaker: {},
        editMode: false
    },
    mounted() {
        this.fetchParticipantSpeakers();
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
        addParticipantSpeaker() {
            axios.post('/participants-speakers', this.newParticipantSpeaker)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.participantSpeakers.push(this.newParticipantSpeaker);
                        this.newParticipantSpeaker = { name: '', email: '', company: '', role: 'Speaker', status: 'Invited' };
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
            axios.put('/participants-speakers', this.currentParticipantSpeaker)
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
        cancelEdit() {
            this.editMode = false;
            this.currentParticipantSpeaker = {};
        }
    }
});
