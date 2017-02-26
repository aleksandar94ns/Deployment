app.service('bidsService', function($http){
    return {
        list: function(onSuccess, onError){
            $http.get('/api/bids').then(onSuccess, onError);
        },
        create: function (bid, onSuccess, onError) {
            $http.post('/api/bids', bid).then(onSuccess, onError);
        },
        listBySupply: function(supply, onSuccess, onError){
            $http.get('/api/bids/supply/' + supply).then(onSuccess, onError);
        },
        listBySeller: function(seller, onSuccess, onError){
            $http.get('/api/bids/seller/' + seller).then(onSuccess, onError);
        },
        patch: function (bid, onSuccess, onError) {
            $http.patch('/api/bids', bid).then(onSuccess, onError);
        },
        put: function (bid, onSuccess, onError) {
            $http.put('/api/bids', bid).then(onSuccess, onError);
        }
    }
});