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
		this.settings.id = this.$element.data('master-detail-id');
		var index = sessionStorage.getItem(this.settings.id);
		var selectedElement = this.$element.find('li > [data-master-detail-index="' + index + '"]');
		if (index != null && isFinite(index) && selectedElement.length > 0) {
			this.select(selectedElement);
		} else {
			this.select(this.$element.find('a:first'));
		}
		this.$element.trigger(this.getNamespacedEvent('initialized'));
	};

	MasterDetail.settings = {
		namespace : 'component-tag-library.master-detail'
	};

	MasterDetail.prototype.getNamespacedEvent = function(event) {
		return event + '.' + this.settings.namespace;
	};

	MasterDetail.prototype.select = function(element) {
		this.$currentItem = $(element);

		this.$element.children('.active').removeClass('active');
		this.$currentItem.parent().addClass('active');
		this.settings.id = this.$element.data('master-detail-id');
		var index = this.$currentItem.data('master-detail-index');
		var $detailContainer = $('[data-master-detail-id="' + this.settings.id + '"][data-master-detail="detail"]');
		$detailContainer.children(':not(.hide)').addClass('hide');
		this.$currentDetail = $detailContainer.children('[data-master-detail-index="' + index + '"]').removeClass('hide');

		sessionStorage.setItem(this.settings.id, index);
		var selectedEvent = jQuery.Event(this.getNamespacedEvent('selected'), {
			detail : this.$currentDetail
		});
		this.$currentItem.trigger(selectedEvent);
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
