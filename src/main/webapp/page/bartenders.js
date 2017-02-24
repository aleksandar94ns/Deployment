app.controller('BartendersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, bartendersService) {

    $scope.page.current = 5;

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
