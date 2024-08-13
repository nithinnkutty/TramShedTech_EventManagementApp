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
                        // Assuming response.data.data contains events with their schedules
                        this.events = response.data.data.map(event => {
                            return {
                                ...event,
                                date: event.schedules.length > 0 ? event.schedules[0].date : '', // use the first schedule as the main date
                                time: event.schedules.length > 0 ? event.schedules[0].startTime + ' - ' + event.schedules[0].endTime : ''
                            };
                        });
                        this.uniqueYears = [...new Set(this.events.map(event => new Date(event.date).getFullYear().toString()))];
                        this.filterEvents(); // Apply initial filters
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

                // Sort by date to ensure proper order
                filtered.sort((a, b) => new Date(a.date) - new Date(b.date));

                // Update filtered events
                this.filteredEvents = filtered;

                // Force re-render after filter update
                this.$nextTick(() => {
                    console.log('Filtered events updated:', this.filteredEvents);
                    this.$forceUpdate();  // Force Vue to re-render the component

                    // Optional: Manually re-trigger dropdown
                    this.$refs.statusDropdown.blur();
                    this.$refs.statusDropdown.focus();
                });
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
        this.$nextTick(() => {
            console.log('Mounted: Dropdowns and events initialized');
        });
    }
});

