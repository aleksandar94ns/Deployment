app.service('drinkCardsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/drinkCards').then(onSuccess, onError);
        },
        create: function (drinkCard, onSuccess, onError) {
            $http.post('/api/drinkCards', drinkCard).then(onSuccess, onError);
        }
    }
});
