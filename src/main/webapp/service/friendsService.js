app.service('friendsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/friends').then(onSuccess, onError);
        },
        listPotentialFriends: function (onSuccess, onError) {
            $http.get('/api/friends/potential').then(onSuccess, onError);
        },
        create: function (recipient, onSuccess, onError) {
            $http.post('/api/friends', recipient).then(onSuccess, onError);
        }
    }
});