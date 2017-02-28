app.service('employeeShiftsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.put('/api/employeeShifts').then(onSuccess, onError);
        },
        create: function (employeeShift, onSuccess, onError) {
            $http.post('/api/employeeShifts', employeeShift).then(onSuccess, onError);
        },
        search: function(date, onSuccess, onError){
            $http.put('/api/employeeShifts/' + date).then(onSuccess, onError);
        }
    }
});
