app.controller('BartendersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, bartendersService) {

    bartendersService.list(function (response) {
        $scope.bartenders = response.data;
    });

    $scope.addBartender = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createBartender.html',
            controller: 'CreateBartenderController'
        }).finally(function () {
            bartendersService.list(function (response) {
                $scope.bartenders = response.data;
            });
        });
    };
});
