app.service('shiftsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/shifts').then(onSuccess, onError);
        },
        create: function (shift, onSuccess, onError) {
            $http.post('/api/shifts', shift).then(onSuccess, onError);
        }
    }
});