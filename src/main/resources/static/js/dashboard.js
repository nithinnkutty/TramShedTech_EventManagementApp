new Vue({
    el: '#app',
    data: {
        events: [],
        filteredEvents: [],
        selectedFilter: 'all',
        showFeedbackModal: false,
        selectedEvent: {},
        feedback: {
            name: '',
            email: '',
            rating: '',
            comments: ''
        }
    },
    methods: {
        fetchEvents() {
            axios.get('/api/events')
                .then(response => {
                    this.events = response.data.data;
                    this.filterEvents();
                })
                .catch(error => {
                    console.error("There was an error fetching the events!", error);
                });
        },
        filterEvents() {
            const now = new Date();
            if (this.selectedFilter === 'upcoming') {
                this.filteredEvents = this.events.filter(event => new Date(event.date) >= now);
            } else if (this.selectedFilter === 'completed') {
                this.filteredEvents = this.events.filter(event => new Date(event.date) < now);
            } else {
                this.filteredEvents = this.events;
            }
        },
        viewEvent(eventId) {
            localStorage.setItem("eventId", eventId);
            window.location.href = `/view-event.html`;
        },
        showFeedbackForm(event) {
            this.selectedEvent = event;
            this.showFeedbackModal = true;
        },
        submitFeedback() {
            axios.post(`/api/events/${this.selectedEvent.id}/feedback`, this.feedback)
                .then(response => {
                    if (response.data.status === 'SUCCESS') {
                        this.$message({
                            message: 'Feedback submitted successfully',
                            type: 'success'
                        });
                        this.closeFeedbackForm();
                    } else {
                        this.$message.error('Failed to submit feedback');
                    }
                })
                .catch(error => {
                    console.error('Error submitting feedback:', error);
                });
        },
        closeFeedbackForm() {
            this.showFeedbackModal = false;
            this.feedback = {
                name: '',
                email: '',
                rating: '',
                comments: ''
            };
        }
    },
    mounted() {
        this.fetchEvents();
    }
});
