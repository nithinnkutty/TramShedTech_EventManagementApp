new Vue({
    el: '#app',
    data: {
        events: [],
        filteredEvents: [],
        selectedFilter: 'all', // Updated the default value for selectedFilter
        selectedYear: 'all',   // Added data property for year filter
        selectedMonth: 'all',  // Added data property for month filter
        uniqueSites: [],
        uniqueYears: [],
        months: [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        showFeedbackModal: false,
        feedbackMode: '',  // To differentiate between viewing and leaving feedback
        selectedEvent: {},
        feedback: {
            name: '',
            email: '',
            rating: '',
            comments: ''
        },
        feedbackList: [], // List of feedbacks for a specific event
        userRole: ''
    },
    methods: {
        fetchEvents() {
            axios.get('/api/events')
                .then(response => {
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
                filtered = filtered.filter(event => new Date(event.date).getMonth() === this.months.indexOf(this.selectedMonth));
            }

            // Sort by date to ensure proper order
            filtered.sort((a, b) => new Date(a.date) - new Date(b.date));

            // Update filtered events
            this.filteredEvents = filtered;

            // Force re-render after filter update
            this.$nextTick(() => {
                console.log('Filtered events updated:', this.filteredEvents);
                this.$forceUpdate();  // Force Vue to re-render the component
            });
        },
        viewEvent(eventId) {
            localStorage.setItem("eventId", eventId);
            window.location.href = /view-event.html;
        },
        // Method to check if the user can view feedback
        canViewFeedback() {
            return ['Senior Staff', 'Department Managers', 'Leaders', 'Chief Executive Officer'].includes(this.userRole);
        }
        // Method to fetch the user's role (assuming it's stored in the session),
        fetchUserRole() {
            axios.get('/user/getUserRole')
                .then(response => {
                    this.userRole = response.data.data;
                })
                .catch(error => {
                    console.error("Error fetching user role:", error);
                });
        },
        viewFeedback(eventId) {
            if (this.canViewFeedback()) {
                this.feedbackMode = 'view';
                // Make an API call to fetch feedback for the given event ID
                axios.get(/api/events/${eventId}/feedback)
                    .then(response => {
                        if (response.data.status === 'SUCCESS') {
                            // Assuming you want to store the feedback and display it in a modal
                            this.feedback = response.data.data;
                            this.showFeedbackModal = true;  // Show the feedback modal
                        } else {
                            this.$message.error('Failed to retrieve feedback.');
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching feedback:', error);
                        this.$message.error('Error fetching feedback.');
                    });
            } else {
                alert("You do not have permission to view feedback.");
            }
        },
        showFeedbackForm(event) {
            this.feedbackMode = 'leave';
            this.selectedEvent = event;
            this.showFeedbackModal = true;
        },
        submitFeedback() {
            axios.post(/api/events/${this.selectedEvent.id}/feedback, this.feedback)
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
        this.fetchUserRole(); // Fetch the user's role when the component mounts
        this.fetchEvents();
        this.selectedYear = 'all';  // Set the default filter for year to 'all'
        this.selectedMonth = 'all';  // Set the default filter for month to 'all'
        this.$nextTick(() => {
            console.log('Mounted: Dropdowns and events initialized');
        });
    }
});