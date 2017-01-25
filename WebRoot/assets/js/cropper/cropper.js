/*!
 * Cropper v0.5.5
 * https://github.com/fengyuanchen/cropper
 *
 * Copyright 2014 Fengyuan Chen
 * Released under the MIT license
 */

(function (factory) {
  if (typeof define === "function" && define.amd) {
    // AMD. Register as anonymous module.
    define(["jquery"], factory);
  } else {
    // Browser globals.
    factory(jQuery);
  }
})(function ($) {

  "use strict";

  var $window = $(window),
      $document = $(document),

      // RegExps
      regexpDirection = /^(\+|\*|e|n|w|s|ne|nw|sw|se)$/i,
      regexpOption = /^(x|y|width|height)$/i,

      // Classes
      classHidden = "cropper-hidden",
      classInvisible = "cropper-invisible",

      // Events
      eventDragStart = "mousedown touchstart",
      eventDragMove = "mousemove touchmove",
      eventDragEnd = "mouseup mouseleave touchend touchleave touchcancel",
      eventBuild = "build.cropper",
      eventBuilt = "built.cropper",
      eventRender = "render.cropper",
      eventResize = "resize.cropper",

      // Functions
      isNumber = function (n) {
        return typeof n === "number";
      },

      // Constructor
      Cropper = function (element, options) {
        this.$element = $(element);
        this.setDefaults(options);
        this.init();
      },

      // Others
      round = Math.round,
      min = Math.min,
      max = Math.max,
      abs = Math.abs,
      num = parseFloat;

  Cropper.prototype = {
    constructor: Cropper,

    setDefaults: function (options) {
      options = $.extend({}, Cropper.defaults, $.isPlainObject(options) ? options : {});

      $.each(options, function (i, n) {
        switch (i) {
          case "moveable":
            options.movable = n;
            break;

          case "resizeable":
            options.resizable = n;
            break;

          case "aspectRatio":
            options[i] = abs(num(n)) || NaN; // 0 -> NaN
            break;

          case "minWidth":
          case "minHeight":
            options[i] = abs(num(n)) || 0; // NaN -> 0
            break;

          case "maxWidth":
          case "maxHeight":
            options[i] = abs(num(n)) || Infinity; // NaN -> Infinity
            break;

          // No default
        }
      });

      this.defaults = options;
    },

    init: function () {
      var _this = this,
          $element = this.$element,
          element = $element[0],
          image = {},
          src,
          $clone;

      if ($element.is("img")) {
        src = $element.attr("src");
      } else if ($element.is("canvas") && element.getContext) {
        src = element.toDataURL();
      }

      if (!src) {
        return;
      }

      this.$clone && this.$clone.remove();
      this.$clone = $clone = $('<img src="' + src + '">');

      $clone.one("load", function () {
        image.naturalWidth = this.naturalWidth || $clone.width();
        image.naturalHeight = this.naturalHeight || $clone.height();
        image.aspectRatio = image.naturalWidth / image.naturalHeight;

        _this.active = true;
        _this.src = src;
        _this.image = image;
        _this.build();
      });

      // Hide and prepend the clone iamge to the document body (Don't append to).
      $clone.addClass(classInvisible).prependTo("body");
    },

    build: function () {
      var $element = this.$element,
          defaults = this.defaults,
          buildEvent,
          $cropper;

      if (this.built) {
        this.unbuild();
      }

      buildEvent = $.Event(eventBuild);
      $element.trigger(buildEvent);

      if (buildEvent.isDefaultPrevented()) {
        return;
      }

      // Create cropper elements
      this.$cropper = ($cropper = $(Cropper.template));

      // Hide the original image
      $element.addClass(classHidden);

      // Show and prepend the clone iamge to the cropper
      this.$clone.removeClass(classInvisible).prependTo($cropper);

      this.$container = $element.parent();
      this.$container.append($cropper);

      this.$modal = $cropper.find(".cropper-modal");
      this.$canvas = $cropper.find(".cropper-canvas");
      this.$dragger = $cropper.find(".cropper-dragger");
      this.$viewer = $cropper.find(".cropper-viewer");

      // Init default settings
      this.cropped = true;

      if (!defaults.autoCrop) {
        this.$dragger.addClass(classHidden);
        this.cropped = false;
      }

      this.$modal.toggleClass(classHidden, !defaults.modal);
      !defaults.dragCrop && this.$canvas.addClass(classHidden);
      !defaults.movable && this.$dragger.find(".cropper-face").addClass(classHidden);
      !defaults.resizable && this.$dragger.find(".cropper-line, .cropper-point").addClass(classHidden);

      this.$dragScope = defaults.multiple ? this.$cropper : $document;

      this.addListener();
      this.initPreview();

      this.built = true;
      this.update();
      $element.trigger(eventBuilt);
    },

    unbuild: function () {
      if (!this.built) {
        return;
      }

      this.built = false;
      this.removeListener();

      this.$preview.empty();
      this.$preview = null;

      this.$dragger = null;
      this.$canvas = null;
      this.$modal = null;
      this.$container = null;

      this.$cropper.remove();
      this.$cropper = null;
    },

    update: function (data) {
      this.initContainer();
      this.initCropper();
      this.initDragger();

      if (data) {
        this.setData(data, true);
      } else {
        this.setData(this.defaults.data);
      }
    },

    resize: function () {
      clearTimeout(this.resizing);
      this.resizing = setTimeout($.proxy(this.update, this, this.getData()), 200);
    },

    reset: function (deep) {
      if (!this.cropped) {
        return;
      }

      if (deep) {
        this.defaults.data = {};
      }

      this.dragger = this.cloneDragger();
      this.setData(this.defaults.data);
    },

    release: function () {
      if (!this.cropped) {
        return;
      }

      this.cropped = false;

      this.defaults.done({
        x: 0,
        y: 0,
        width: 0,
        height: 0
      });

      this.$dragger.addClass(classHidden);
    },

    destroy: function () {
      var $element = this.$element;

      if (!this.active) {
        return;
      }

      this.unbuild();
      $element.removeClass(classHidden);
      $element.removeData("cropper");
      $element = null;
    },

    preview: function () {
      var cropper = this.cropper,
          dragger = this.dragger;

      this.$viewer.find("img").css({
        height: round(cropper.height),
        marginLeft: - round(dragger.left),
        marginTop: - round(dragger.top),
        width: round(cropper.width)
      });

      this.$preview.each(function () {
        var $this = $(this),
            ratio = $this.width() / dragger.width,
            styles = {
              height: round(cropper.height * ratio),
              marginLeft: - round(dragger.left * ratio),
              marginTop: - round(dragger.top * ratio),
              width: round(cropper.width * ratio)
            };

        $this.find("img").css(styles);
      });
    },

    addListener: function () {
      var defaults = this.defaults;

      this.$element.on(eventBuild, defaults.build).on(eventBuilt, defaults.built).on(eventRender, defaults.render);

      this.$cropper.on(eventDragStart, $.proxy(this.dragstart, this));

      this.$dragScope.on(eventDragMove, $.proxy(this.dragmove, this)).on(eventDragEnd, $.proxy(this.dragend, this));

      $window.on(eventResize, $.proxy(this.resize, this));
    },

    removeListener: function () {
      var defaults = this.defaults;

      this.$element.off(eventBuild, defaults.build).off(eventBuilt, defaults.built).off(eventRender, defaults.render);

      this.$cropper.off(eventDragStart, this.dragstart);

      this.$dragScope.off(eventDragMove, this.dragmove).on(eventDragEnd, this.dragend);

      $window.off(eventResize, this.resize);
    },

    initPreview: function () {
      var img = '<img src="' + this.src + '">';

      this.$preview = $(this.defaults.preview);
      this.$preview.html(img);
      this.$viewer.html(img);
    },

    initContainer: function () {
      var $container = this.$container;

      this.container = {
        width: $container.width(),
        height: $container.height()
      };
    },

    initCropper: function () {
      var container = this.container,
          image = this.image,
          cropper;

      if (((image.naturalWidth * container.height / image.naturalHeight) - container.width) >= 0) {
        cropper = {
          height: container.width / image.aspectRatio,
          width: container.width,
          left: 0
        };

        cropper.top = (container.height - cropper.height) / 2;
      } else {
        cropper = {
          height: container.height,
          width: container.height * image.aspectRatio,
          top: 0
        };

        cropper.left = (container.width - cropper.width) / 2;
      }

      image.ratio = cropper.width / image.naturalWidth;
      image.height = cropper.height;
      image.width = cropper.width;

      this.$cropper.css({
        height: round(cropper.height),
        left: round(cropper.left),
        top: round(cropper.top),
        width: round(cropper.width)
      });

      this.cropper = cropper;
    },

    initDragger: function () {
      var defaults = this.defaults,
          cropper = this.cropper,
          // If not set, use the original aspect ratio of the image.
          aspectRatio = defaults.aspectRatio || this.image.aspectRatio,
          ratio = this.image.ratio,
          dragger;

      if (((cropper.height * aspectRatio) - cropper.width) >= 0) {
        dragger = {
          height: cropper.width / aspectRatio,
          width: cropper.width,
          left: 0,
          top: (cropper.height - (cropper.width / aspectRatio)) / 2,
          maxWidth: cropper.width,
          maxHeight: cropper.width / aspectRatio
        };
      } else {
        dragger = {
          height: cropper.height,
          width: cropper.height * aspectRatio,
          left: (cropper.width - (cropper.height * aspectRatio)) / 2,
          top: 0,
          maxWidth: cropper.height * aspectRatio,
          maxHeight: cropper.height
        };
      }

      dragger.minWidth = 0;
      dragger.minHeight = 0;

      if (defaults.aspectRatio) {
        if (isFinite(defaults.maxWidth)) {
          dragger.maxWidth = min(dragger.maxWidth, defaults.maxWidth * ratio);
          dragger.maxHeight = dragger.maxWidth / aspectRatio;
        } else if (isFinite(defaults.maxHeight)) {
          dragger.maxHeight = min(dragger.maxHeight, defaults.maxHeight * ratio);
          dragger.maxWidth = dragger.maxHeight * aspectRatio;
        }

        if (defaults.minWidth > 0) {
          dragger.minWidth = max(0, defaults.minWidth * ratio);
          dragger.minHeight = dragger.minWidth / aspectRatio;
        } else if (defaults.minHeight > 0) {
          dragger.minHeight = max(0, defaults.minHeight * ratio);
          dragger.minWidth = dragger.minHeight * aspectRatio;
        }
      } else {
        dragger.maxWidth = min(dragger.maxWidth, defaults.maxWidth * ratio);
        dragger.maxHeight = min(dragger.maxHeight, defaults.maxHeight * ratio);
        dragger.minWidth = max(0, defaults.minWidth * ratio);
        dragger.minHeight = max(0, defaults.minHeight * ratio);
      }

      // minWidth can't be greater than maxWidth, and minHeight too.
      dragger.minWidth = min(dragger.maxWidth, dragger.minWidth);
      dragger.minHeight = min(dragger.maxHeight, dragger.minHeight);

      // Center the dragger by default
      dragger.height *= 0.8;
      dragger.width *= 0.8;
      dragger.left = (cropper.width - dragger.width) / 2;
      dragger.top = (cropper.height - dragger.height) / 2;

      this.defaultDragger = dragger;
      this.dragger = this.cloneDragger();
      this.draggerLeft = dragger.left;
      this.draggerTop = dragger.top;
    },

    cloneDragger: function () {
      return $.extend({}, this.defaultDragger);
    },

    renderDragger: function () {
      var dragger = this.dragger,
          cropper = this.cropper,
          left = this.draggerLeft,
          top = this.draggerTop,
          maxLeft,
          maxTop,
          renderEvent;

      if (dragger.width > dragger.maxWidth) {
        dragger.width = dragger.maxWidth;
        dragger.left = left;
      } else if (dragger.width < dragger.minWidth) {
        dragger.width = dragger.minWidth;
        dragger.left = left;
      }

      if (dragger.height > dragger.maxHeight) {
        dragger.height = dragger.maxHeight;
        dragger.top = top;
      } else if (dragger.height < dragger.minHeight) {
        dragger.height = dragger.minHeight;
        dragger.top = top;
      }

      maxLeft = cropper.width - dragger.width;
      maxTop = cropper.height - dragger.height;
      dragger.left = dragger.left > maxLeft ? maxLeft : dragger.left < 0 ? 0 : dragger.left;
      dragger.top = dragger.top > maxTop ? maxTop : dragger.top < 0 ? 0 : dragger.top;

      // Trigger the render event
      renderEvent = $.Event(eventRender);
      this.$element.trigger(renderEvent);

      if (renderEvent.isDefaultPrevented()) {
        return;
      }

      // Re-render the dragger
      this.dragger = dragger;
      this.draggerLeft = dragger.left;
      this.draggerTop = dragger.top;
      this.defaults.done(this.getData());

      this.$dragger.css({
        height: round(dragger.height),
        left: round(dragger.left),
        top: round(dragger.top),
        width: round(dragger.width)
      });

      this.preview();
    },

    setData: function (data, once) {
      var cropper = this.cropper,
          dragger = this.dragger,
          aspectRatio = this.defaults.aspectRatio;

      if (!this.built || typeof data === "undefined") {
        return;
      }

      if (data === null || $.isEmptyObject(data)) {
        dragger = this.cloneDragger();
      }

      if ($.isPlainObject(data) && !$.isEmptyObject(data)) {

        if (!once) {
          this.defaults.data = data;
        }

        data = this.transformData(data);

        if (isNumber(data.x) && data.x <= cropper.width) {
          dragger.left = data.x;
        }

        if (isNumber(data.y) && data.y <= cropper.height) {
          dragger.top = data.y;
        }

        if (aspectRatio) {
          if (isNumber(data.width) && data.width <= dragger.maxWidth && data.width >= dragger.minWidth) {
            dragger.width = data.width;
            dragger.height = dragger.width / aspectRatio;
          } else if (isNumber(data.height) && data.height <= dragger.maxHeight && data.height >= dragger.minHeight) {
            dragger.height = data.height;
            dragger.width = dragger.height * aspectRatio;
          }
        } else {
          if (isNumber(data.width) && data.width <= dragger.maxWidth && data.width >= dragger.minWidth) {
            dragger.width = data.width;
          }

          if (isNumber(data.height) && data.height <= dragger.maxHeight && data.height >= dragger.minHeight) {
            dragger.height = data.height;
          }
        }
      }

      this.dragger = dragger;
      this.renderDragger();
    },

    getData: function () {
      var dragger = this.dragger,
          data = {};

      if (this.built) {
        data = {
          x: dragger.left,
          y: dragger.top,
          width: dragger.width,
          height: dragger.height
        };

        data = this.transformData(data, true);
      }

      return data;
    },

    transformData: function (data, reverse) {
      var ratio = this.image.ratio,
          result = {};

      $.each(data, function (i, n) {
        n = num(n);

        if (regexpOption.test(i) && !isNaN(n)) {
          // Not round when set data.
          result[i] = reverse ? round(n / ratio) : n * ratio;
        }
      });

      return result;
    },

    setAspectRatio: function (aspectRatio) {
      var freeRatio = aspectRatio === "auto";

      aspectRatio = num(aspectRatio);

      if (freeRatio || (!isNaN(aspectRatio) && aspectRatio > 0)) {
        this.defaults.aspectRatio = freeRatio ? NaN : aspectRatio;

        if (this.built) {
          this.initDragger();
          this.renderDragger();
        }
      }
    },

    setImgSrc: function (src) {
      var _this = this,
          $element = this.$element,
          element = $element[0],
          context;

      if (src && src !== this.src) {
        if ($element.is("img")) {
          $element.attr("src", src);
          this.init();
        } else if ($element.is("canvas") && element.getContext) {
          context = element.getContext("2d");

          $('<img src="' + src + '">').one("load", function () {
            element.width = this.width;
            element.height = this.height;
            context.clearRect(0, 0, element.width, element.height);
            context.drawImage(this, 0, 0);
            _this.init();
          });
        }
      }
    },

    getImgInfo: function () {
      return this.image || {};
    },

    dragstart: function (event) {
      var touches = (event.originalEvent || event).touches,
          e = event,
          direction;

      if (touches) {
        if (touches.length > 1) {
          return;
        }

        e = touches[0];
        this.touchId = e.identifier;
      }

      direction = $(e.target).data("direction");

      if (regexpDirection.test(direction)) {
        event.preventDefault();

        this.direction = direction;
        this.startX = e.pageX;
        this.startY = e.pageY;

        if (direction === "+") {
          this.cropping = true;
          this.$modal.removeClass(classHidden);
        }
      }
    },

    dragmove: function (event) {
      var touches = (event.originalEvent || event).changedTouches,
          e = event;

      if (touches) {
        if (touches.length > 1) {
          return;
        }

        e = touches[0];

        if (e.identifier !== this.touchId) {
          return;
        }
      }

      if (this.direction) {
        event.preventDefault();

        this.endX = e.pageX;
        this.endY = e.pageY;
        this.dragging();
      }
    },

    dragend: function (event) {
      var touches = (event.originalEvent || event).changedTouches,
          e = event;

      if (touches) {
        if (touches.length > 1) {
          return;
        }

        e = touches[0];

        if (e.identifier !== this.touchId) {
          return;
        }
      }

      if (this.direction) {
        event.preventDefault();

        if (this.cropping) {
          this.cropping = false;
          this.$modal.toggleClass(classHidden, !this.defaults.modal);
        }

        this.direction = "";
      }
    },

    dragging: function () {
      var direction = this.direction,
          cropper = this.cropper,
          maxWidth = cropper.width,
          maxHeight = cropper.height,
          dragger = this.dragger,
          width = dragger.width,
          height = dragger.height,
          left = dragger.left,
          top = dragger.top,
          right = left + width,
          bottom = top + height,
          renderable = true,
          aspectRatio = this.defaults.aspectRatio,
          range = {
            x: this.endX - this.startX,
            y: this.endY - this.startY
          },
          offset;

      if (aspectRatio) {
        range.X = range.y * aspectRatio;
        range.Y = range.x / aspectRatio;
      }

      switch (direction) {

        // cropping
        case "+":
          if (range.x && range.y) {
            offset = this.$cropper.offset();
            left = this.startX - offset.left;
            top = this.startY - offset.top;
            width = dragger.minWidth;
            height = dragger.minHeight;

            if (range.x > 0) {
              if (range.y > 0) {
                direction = "se";
              } else {
                direction = "ne";
                top -= height;
              }
            } else {
              if (range.y > 0) {
                direction = "sw";
                left -= width;
              } else {
                direction = "nw";
                left -= width;
                top -= height;
              }
            }

            // Show the dragger if is hidden
            if (!this.cropped) {
              this.cropped = true;
              this.$dragger.removeClass(classHidden);
            }
          }

          break;

        // moving
        case "*":
          left += range.x;
          top += range.y;

          break;

        // resizing
        case "e":
          if (range.x >= 0 && (right >= maxWidth || aspectRatio && (top <= 0 || bottom >= maxHeight))) {
            renderable = false;
            break;
          }

          width += range.x;

          if (aspectRatio) {
            height = width / aspectRatio;
            top -= range.Y / 2;
          }

          if (width < 0) {
            direction = "w";
            width = 0;
          }

          break;

        case "n":
          if (range.y <= 0 && (top <= 0 || aspectRatio && (left <= 0 || right >= maxWidth))) {
            renderable = false;
            break;
          }

          height -= range.y;
          top += range.y;

          if (aspectRatio) {
            width = height * aspectRatio;
            left += range.X / 2;
          }

          if (height < 0) {
            direction = "s";
            height = 0;
          }

          break;

        case "w":
          if (range.x <= 0 && (left <= 0 || aspectRatio && (top <= 0 || bottom >= maxHeight))) {
            renderable = false;
            break;
          }

          width -= range.x;
          left += range.x;

          if (aspectRatio) {
            height = width / aspectRatio;
            top += range.Y / 2;
          }

          if (width < 0) {
            direction = "e";
            width = 0;
          }

          break;

        case "s":
          if (range.y >= 0 && (bottom >= maxHeight || aspectRatio && (left <= 0 || right >= maxWidth))) {
            renderable = false;
            break;
          }

          height += range.y;

          if (aspectRatio) {
            width = height * aspectRatio;
            left -= range.X / 2;
          }

          if (height < 0) {
            direction = "n";
            height = 0;
          }

          break;

        case "ne":
          if (range.y <= 0 && (top <= 0 || right >= maxWidth)) {
            renderable = false;
            break;
          }

          height -= range.y;
          top += range.y;

          if (aspectRatio) {
            width = height * aspectRatio;
          } else {
            width += range.x;
          }

          if (height < 0) {
            direction = "sw";
            height = 0;
            width = 0;
          }

          break;

        case "nw":
          if (range.y <= 0 && (top <= 0 || left <= 0)) {
            renderable = false;
            break;
          }

          height -= range.y;
          top += range.y;

          if (aspectRatio) {
            width = height * aspectRatio;
            left += range.X;
          } else {
            width -= range.x;
            left += range.x;
          }

          if (height < 0) {
            direction = "se";
            height = 0;
            width = 0;
          }

          break;

        case "sw":
          if (range.x <= 0 && (left <= 0 || bottom >= maxHeight)) {
            renderable = false;
            break;
          }

          width -= range.x;
          left += range.x;

          if (aspectRatio) {
            height = width / aspectRatio;
          } else {
            height += range.y;
          }

          if (width < 0) {
            direction = "ne";
            height = 0;
            width = 0;
          }

          break;

        case "se":
          if (range.x >= 0 && (right >= maxWidth || bottom >= maxHeight)) {
            renderable = false;
            break;
          }

          width += range.x;

          if (aspectRatio) {
            height = width / aspectRatio;
          } else {
            height += range.y;
          }

          if (width < 0) {
            direction = "nw";
            height = 0;
            width = 0;
          }

          break;

        // No default
      }

      if (renderable) {
        dragger.width = width;
        dragger.height = height;
        dragger.left = left;
        dragger.top = top;
        this.direction = direction;

        this.renderDragger();
      }

      // Override
      this.startX = this.endX;
      this.startY = this.endY;
    }
  };

  // Use the string compressor: Strmin (https://github.com/fengyuanchen/strmin)
  Cropper.template = (function(a,b){b=b.split(",");return a.replace(/\d+/g,function(c){return b[c];});})('<0 6="5-container"><0 6="5-modal"></0><0 6="5-canvas" 3-2="+"></0><0 6="5-dragger"><1 6="5-viewer"></1><1 6="5-8 8-h"></1><1 6="5-8 8-v"></1><1 6="5-face" 3-2="*"></1><1 6="5-7 7-e" 3-2="e"></1><1 6="5-7 7-n" 3-2="n"></1><1 6="5-7 7-w" 3-2="w"></1><1 6="5-7 7-s" 3-2="s"></1><1 6="5-4 4-e" 3-2="e"></1><1 6="5-4 4-n" 3-2="n"></1><1 6="5-4 4-w" 3-2="w"></1><1 6="5-4 4-s" 3-2="s"></1><1 6="5-4 4-ne" 3-2="ne"></1><1 6="5-4 4-nw" 3-2="nw"></1><1 6="5-4 4-sw" 3-2="sw"></1><1 6="5-4 4-se" 3-2="se"></1></0></0>',"div,span,direction,data,point,cropper,class,line,dashed");

  /* Template source:
  <div class="cropper-container">
    <div class="cropper-modal"></div>
    <div class="cropper-canvas" data-direction="+"></div>
    <div class="cropper-dragger">
      <span class="cropper-viewer"></span>
      <span class="cropper-dashed dashed-h"></span>
      <span class="cropper-dashed dashed-v"></span>
      <span class="cropper-face" data-direction="*"></span>
      <span class="cropper-line line-e" data-direction="e"></span>
      <span class="cropper-line line-n" data-direction="n"></span>
      <span class="cropper-line line-w" data-direction="w"></span>
      <span class="cropper-line line-s" data-direction="s"></span>
      <span class="cropper-point point-e" data-direction="e"></span>
      <span class="cropper-point point-n" data-direction="n"></span>
      <span class="cropper-point point-w" data-direction="w"></span>
      <span class="cropper-point point-s" data-direction="s"></span>
      <span class="cropper-point point-ne" data-direction="ne"></span>
      <span class="cropper-point point-nw" data-direction="nw"></span>
      <span class="cropper-point point-sw" data-direction="sw"></span>
      <span class="cropper-point point-se" data-direction="se"></span>
    </div>
  </div>
  */

  Cropper.defaults = {
    // Basic
    aspectRatio: "auto",
    data: {}, // Allow options: x, y, width, height
    done: $.noop,
    // preview: undefined,

    // Toggles
    multiple: false,
    autoCrop: true,
    dragCrop: true,
    modal: true,
    movable: true,
    resizable: true,

    // Dimensions
    minWidth: 0,
    minHeight: 0,
    maxWidth: Infinity,
    maxHeight: Infinity
  };

  Cropper.setDefaults = function (options) {
    $.extend(Cropper.defaults, options);
  };

  // Reference the old cropper
  Cropper.other = $.fn.cropper;

  // Register as jQuery plugin
  $.fn.cropper = function (options, settings) {
    var result = this;

    this.each(function () {
      var $this = $(this),
          data = $this.data("cropper");

      if (!data) {
        $this.data("cropper", (data = new Cropper(this, options)));
      }

      if (typeof options === "string" && $.isFunction(data[options])) {
        result = data[options](settings);
      }
    });

    return (typeof result !== "undefined" ? result : this);
  };

  $.fn.cropper.constructor = Cropper;
  $.fn.cropper.setDefaults = Cropper.setDefaults;

  // No conflict
  $.fn.cropper.noConflict = function () {
    $.fn.cropper = Cropper.other;
    return this;
  };
});
