app.controller('ReservationsController', function ($scope, reservationsService) {

    $scope.page.current = 15;

    var loadData = function () {
        reservationsService.listMy(function (response) {
            $scope.pendingReservations = response.data.filter(function(reservation) {
                return reservation.status === 'PENDING';
            });
            $scope.acceptedReservations = response.data.filter(function(reservation) {
                return reservation.status === 'ACCEPTED';
            });
        });
    };

    loadData();

    // Respond menu

    var originatorEv;

    $scope.openMenu = function($mdOpenMenu, ev) {
        originatorEv = ev;
        $mdOpenMenu(ev);
    };

    $scope.accept = function(reservation, ev) {
        reservation.status = 'ACCEPTED';
        reservationsService.update(reservation, function () {
            loadData();
        });
        originatorEv = null;
    };

    $scope.decline = function(reservation, ev) {
        reservation.status = 'DECLINED';
        reservationsService.update(reservation, function () {
            loadData();
        });
        originatorEv = null;
    };
});
