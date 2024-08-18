new Vue({
        el: '#event',
        data() {
            return {
                formData: {
                    title: '',
                    schedules: [{ date: '', startTime: '', endTime: '' }], // Initialize schedules array
                    speaker: '',
                    location: '',
                    // catering fields
                    cateringType: '',
                    cateringCount: 0,
                    beverageType: '',
                    dietaryRequirements: '',
                    cateringServingTime: '',
                    // Initialize table data
                    tableData: [{
                        type: '',
                        staff: '',
                        company: '',
                        contact: '',
                        email: '',
                        location: '',
                        note: ' '
                    }],
                },
                speakers: [],
                room: [{
                    value: '',
                    label: ''
                }],
                type: [{
                    value: 'option 1',
                    label: 'Networking'
                }, {
                    value: 'option 2',
                    label: 'Panels'
                }, {
                    value: '3',
                    label: 'Speakers'
                }, {
                    value: 'option 4',
                    label: 'Q&A’s'
                }, {
                    value: 'option 5',
                    label: 'Presentations'
                },
                {
                    value: 'option 6',
                    label: 'Social Gatherings'
                }, {
                    value: 'option 7',
                    label: 'Markets'
                },{
                    value: 'option 8',
                    label: 'Miscellaneous'
                },{
                    value: 'option 9',
                    label: 'Festival'
                },{
                    value: 'option 10',
                    label: 'Conference '
                }
                ],
            };
        },
        created(){
            this.fecthRooms();
            this.fetchSpeakers();
        },
        methods: {
            fecthRooms() {
                axios.get('/booking/getRoom')
                    .then(response => {
                        if (!response.data || !Array.isArray(response.data)) {
                            console.error('No data or data is not an array');
                            return;
                        }
                        this.room = response.data.map((fullAddress, index) => {
                            console.log(fullAddress);
                            if (!fullAddress) {
                                console.error('Invalid full address encountered', fullAddress);
                                return { value: `${index}`, label: 'Invalid address' };
                            }
                            return {
                                value: `${index}`,
                                label: fullAddress
                            };
                        });
                    })
                    .catch(error => {
                        console.error('Failed to fetch rooms:', error);
                    });
            },
            fetchRoomsForLocation() {
                axios.get(`/api/events/rooms/${this.formData.location}`)
                    .then(response => {
                        console.log(response.data);
                        if (!response.data || !Array.isArray(response.data)) {
                            console.error('No data or data is not an array');
                            return;
                        }
                        this.room = response.data.map(room => ({
                            value: room.id,
                            label: `${room.roomName} - ${room.locationName} - ${room.postcode}`

                        }));
                    })
                    .catch(error => {
                        console.error('Failed to fetch corresponding rooms:', error);
                    });
            },
            fetchSpeakers() {
                        axios.get('/participants-speakers')  // Assuming this endpoint returns the list of speakers
                            .then(response => {
                                if (response.data.status === 'SUCCESS' && Array.isArray(response.data.data)) {
                                    this.speakers = response.data.data.map(speaker => ({
                                        value: speaker.id,
                                        label: `${speaker.name} (${speaker.company})`
                                    }));
                                } else {
                                    console.error('Failed to fetch speakers:', response);
                                }
                            })
                            .catch(error => {
                                console.error('Error fetching speakers:', error);
                            });
            },
            addSchedule() {
                this.formData.schedules.push({ date: '', startTime: '', endTime: '' });
            },
            removeSchedule(index) {
                this.formData.schedules.splice(index, 1);
            },
            handleButtonClick() {
                const invalidSchedule = this.formData.schedules.some(schedule => !schedule.date || !schedule.startTime || !schedule.endTime);
                if (!this.formData.title || invalidSchedule || !this.formData.location || !this.formData.room) {
                    alert('Please fill in all required fields.');
                    return;
                }
                this.getFormData();
            },
            getFormData() {
            const eventData = {
                title: this.formData.title,
                schedules: this.formData.schedules.map(schedule => ({
                     date: schedule.date,
                     startTime: schedule.startTime,
                     endTime: schedule.endTime
                })),
                room: this.formData.room,
                type: this.formData.type,
                speaker: this.formData.speaker,
                location: this.formData.location,
                staff: this.formData.tableData[0].staff,
                tag: this.formData.tableData[0].tag,
                company: this.formData.tableData[0].company,
                contact: this.formData.tableData[0].contact,
                email: this.formData.tableData[0].email,
                responsible: this.formData.tableData[0].responsible,
                note: this.formData.tableData[0].note,
                // Catering fields
                cateringType: this.formData.cateringType,
                cateringCount: this.formData.cateringCount,
                beverageType: this.formData.beverageType,
                dietaryRequirements: this.formData.dietaryRequirements,
                cateringServingTime: this.formData.cateringServingTime
            };
            console.log('Prepared event data:', eventData);
            axios.post('/api/events/create', eventData)
            .then(response => {
                console.log('Response from server:', response.data);
                if (response.data.data > 0) {
                    console.log('Event created successfully', response);
                    localStorage.setItem("eventId", response.data.data);
                    window.location.href = '/view-event.html';
                } else {
                    alert('Event not created');
                }
            })
            .catch(error => {
                console.log('Error creating event:', error.response);
            });
        }
        }
    });