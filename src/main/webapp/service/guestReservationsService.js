app.service('guestReservationsService', function($http){
    return {
        create: function (reservation, onSuccess, onError) {
            $http.put('/api/guestReservations', reservation).then(onSuccess, onError);
        },
        delete: function (reservation, onSuccess, onError) {
            $http.delete('/api/guestReservations/' + reservation.id).then(onSuccess, onError);
        }
    }
});