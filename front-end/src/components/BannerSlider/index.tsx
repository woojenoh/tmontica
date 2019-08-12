import React from "react";
import Slider from "react-slick";
import { IBanner } from "../../types/banner";
import { IMAGE_URL } from "../../constants";

// Import css files
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./styles.scss";
import { Link } from "react-router-dom";

interface Props {
  banners: IBanner[];
}

const Image = ({ src }: { src: string }) => <img src={src} alt="배너" />;

export class BannerSlider extends React.PureComponent<Props> {
  render() {
    const { banners } = this.props;

    const settings = {
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      arrows: true,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            centerMode: true,
            arrows: false
          }
        }
      ]
    };
    return (
      <Slider {...settings} className="banner">
        {banners.map((banner, i) => {
          if (!banner.imgUrl || typeof banner.imgUrl === "undefined") {
            return "";
          }

          return (
            <div key={i} className="banner__item">
              {/^https?:\/\//.test(banner.link) ? (
                <a href={banner.link} target="blank">
                  <Image src={`${IMAGE_URL}/${banner.imgUrl}`} />
                </a>
              ) : (
                <Link to={banner.link}>
                  <Image src={`${IMAGE_URL}/${banner.imgUrl}`} />
                </Link>
              )}
            </div>
          );
        })}
      </Slider>
    );
  }
}
