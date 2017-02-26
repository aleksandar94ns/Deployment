app.service('reservationsService', function($http){
    return {
        listMy: function(onSuccess, onError){
            $http.get('/api/reservations/me').then(onSuccess, onError);
        },
        create: function (reservation, onSuccess, onError) {
            $http.post('/api/reservations', reservation).then(onSuccess, onError);
        },
        update: function (reservation, onSuccess, onError) {
            $http.patch('/api/reservations', reservation).then(onSuccess, onError);
        }
    }
});