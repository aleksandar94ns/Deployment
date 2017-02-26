app.service('reservationsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/reservations').then(onSuccess, onError);
        },
        create: function (reservation, onSuccess, onError) {
            $http.post('/api/reservations', reservation).then(onSuccess, onError);
        }
    }
});