+function($) {
	'use strict';
	var MasterDetail = function(element, settings) {
		this.settings = $.extend(true, {}, MasterDetail.settings, settings);
		this.$element = $(element);
		var that = this;
		this.$element.on('click', 'a', function(event) {
			event.preventDefault();
			that.selectEventHandler(event.target);
		});
		var id = this.$element.data('master-detail-id');
		var index = sessionStorage.getItem(id);
		if (index != null && isFinite(index)) {
			this.select(this.$element.find('li > [data-master-detail-index="' + index + '"]'));
		} else {
			$('[data-master-detail-id="' + id + '"][data-master-detail="master"] > :not(.nav-header):first').addClass('active');
			$('[data-master-detail-id="' + id + '"][data-master-detail="detail"] > :first').removeClass('hide');
		}
	};

	MasterDetail.settings = {
		namespace : 'component-tag-library.master-detail'
	};

	MasterDetail.prototype.getNamespacedEvent = function(event) {
		return event + '.' + this.settings.namespace;
	};

	MasterDetail.prototype.select = function(element) {
		var $masterItem = $(element);

		this.$element.children('.active').removeClass('active');
		$masterItem.parent().addClass('active');
		var id = this.$element.data('master-detail-id');
		var index = $masterItem.data('master-detail-index');
		var $detailContainer = $('[data-master-detail-id="' + id + '"][data-master-detail="detail"]');
		$detailContainer.children(':not(.hide)').addClass('hide');
		$detailContainer.children('[data-master-detail-index="' + index + '"]').removeClass('hide');

		sessionStorage.setItem(id, index);
	};

	MasterDetail.prototype.selectEventHandler = function(element) {
		var $masterItem = $(element);
		var currentIndex = sessionStorage.getItem(this.$element.data('master-detail-id'));
		if (currentIndex == null || isNaN(currentIndex)) {
			currentIndex = 0;
		}
		var event = jQuery.Event(this.getNamespacedEvent('select'), {currentIndex: currentIndex, masterList: this.$element});
		$masterItem.trigger(event);
		if (!event.isDefaultPrevented()) {
			this.select(element);
		}
	};

	$.fn.masterDetail = function(settings) {
		var pluginArguments = arguments;
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('comp.master-detail');
			if (!data) {
				data = new MasterDetail(this, settings);
				$this.data('comp.master-detail', data);
			}
			if (settings == 'select') {
				data[settings](pluginArguments[1]);
			}
		});
	};

	$.fn.masterDetail.Constructor = MasterDetail;

	$(document).on('ready', function() {
		$('[data-master-detail-id][data-master-detail="master"]').each(function(index, element) {
			$(element).masterDetail();
		});
	});

}(jQuery);