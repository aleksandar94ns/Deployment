app.controller('ShiftsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, shiftsService) {

    $scope.page.current = 17;

    $scope.shifts = [];
    $scope.parsedShifts = [];

    var loadShifts = function () {
        shiftsService.list(function (response) {
            $scope.shifts = [];
            $scope.parsedShifts = [];
            $scope.shifts = response.data;
            $scope.shifts.forEach(function (shift) {
                shift.startHour = moment(shift.startHour).format("HH:mm:ss");
                shift.endHour = moment(shift.endHour).format("HH:mm:ss");
                $scope.parsedShifts.push(shift);
            })
        });
    };

    loadShifts();

    $scope.addShift = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createShift.html',
            controller: 'CreateShiftController'
        }).finally(function () {
            loadShifts();
        });
    };
});