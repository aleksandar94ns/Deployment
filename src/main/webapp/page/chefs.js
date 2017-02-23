app.controller('ChefsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, chefsService) {

    $scope.page.current = 9;

    chefsService.list(function (response) {
        $scope.chefs = response.data;
    });

    $scope.addChef = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createChef.html',
            controller: 'CreateChefController'
        }).finally(function () {
            chefsService.list(function (response) {
                $scope.chefs = response.data;
            });
        });
    };
});