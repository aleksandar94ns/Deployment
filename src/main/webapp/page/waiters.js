app.controller('WaitersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, waitersService) {

    waitersService.list(function (response) {
        $scope.waiters = response.data;
    });

    $scope.addWaiter = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createWaiter.html',
            controller: 'CreateWaiterController'
        }).finally(function () {
            waitersService.list(function (response) {
                $scope.waiters = response.data;
            });
        });
    };

});
