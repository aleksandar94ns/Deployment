app.service('drinkItemsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/drinkItems').then(onSuccess, onError);
        },
        create: function (drinkItem, onSuccess, onError) {
            $http.post('/api/drinkItems', drinkItem).then(onSuccess, onError);
        }
    }
});
