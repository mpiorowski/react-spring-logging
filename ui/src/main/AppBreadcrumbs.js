import React, {memo} from 'react';
import {Breadcrumb, Icon} from "antd";
import {NavLink} from "react-router-dom";
import "./AppBreadcrumb.less";
import {useBreadcrumbsState} from "../context/GlobalContext";

const AppBreadcrumbs = memo(props => {

  const {location} = props;
  const {breadcrumbs} = useBreadcrumbsState();

  const pathSnippets = location.pathname.split('/').filter(i => i);
  let extraBreadcrumbItems = [];

  if (breadcrumbs) {
    extraBreadcrumbItems = pathSnippets.map((_, index, arr) => {
      const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
      if (breadcrumbs[url] !== '' && breadcrumbs[url] !== undefined) {
        if (arr.length - 1 === index) {
          return (
            <Breadcrumb.Item key={url}>
                <span>
                  {breadcrumbs[url]}
                </span>
            </Breadcrumb.Item>
          );
        } else {
          return (
            <Breadcrumb.Item key={url}>
              <NavLink to={url}>
                {breadcrumbs[url]}
              </NavLink>
            </Breadcrumb.Item>
          );
        }
      }
      return '';
    });
  }

  const breadcrumbItems = () => {
      if (extraBreadcrumbItems.length > 0) {
        return[(
          <Breadcrumb.Item key="home">
            <NavLink to="/"><Icon type="home"/> Strona główna</NavLink>
          </Breadcrumb.Item>
        )].concat(extraBreadcrumbItems);
    } else {
        return <Breadcrumb.Item key="home">
          <Icon type="home"/> Strona główna
        </Breadcrumb.Item>
    }
  };

  return (
    <div className={'header-breadcrumb'}>
      <Breadcrumb>
        {breadcrumbItems()}
      </Breadcrumb>
    </div>
  );

});

export default AppBreadcrumbs;
