new Vue({
    el: '#app',
    data: {
        events: [],
        filteredEvents: [],
        selectedFilter: 'All Events',
        uniqueSites: [],
        uniqueYears: [],
        months: [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
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
                    this.uniqueYears = [...new Set(this.events.map(event => new Date(event.date).getFullYear().toString()))];
                    this.filterEvents();
                })
                .catch(error => {
                    console.error("There was an error fetching the events!", error);
                });
        },
        filterEvents() {
            let filtered = this.events;

            if (this.selectedFilter === 'upcoming') {
                filtered = filtered.filter(event => new Date(event.date) > new Date());
            } else if (this.selectedFilter === 'completed') {
                filtered = filtered.filter(event => new Date(event.date) < new Date());
            }

            if (this.selectedYear !== 'all') {
                filtered = filtered.filter(event => new Date(event.date).getFullYear().toString() === this.selectedYear);
            }

            if (this.selectedMonth !== 'all') {
                filtered = filtered.filter(event => new Date(event.date).getMonth() === this.months.indexOf(this.selectedMonth) - 1);
            }

            this.filteredEvents = filtered;
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
        this.selectedYear = 'all';  // Set the default filter for year to 'all'
        this.selectedMonth = 'all';  // Set the default filter for month to 'all'
    }
});

