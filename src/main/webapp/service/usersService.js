app.service('usersService', function($http){
    return {
        listGuests: function(onSuccess, onError){
            $http.get('/api/users/guests').then(onSuccess, onError);
        }
    }
});