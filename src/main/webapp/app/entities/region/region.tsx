import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './region.reducer';
import { IRegion } from 'app/shared/model/region.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRegionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Region extends React.Component<IRegionProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { regionList, match } = this.props;
    return (
      <div>
        <h2 id="region-heading">
          Regions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Region
          </Link>
        </h2>
        <div className="table-responsive">
          {regionList && regionList.length > 0 ? (
            <Table responsive aria-describedby="region-heading">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Region Name</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {regionList.map((region, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${region.id}`} color="link" size="sm">
                        {region.id}
                      </Button>
                    </td>
                    <td>{region.regionName}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${region.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${region.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${region.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Regions found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ region }: IRootState) => ({
  regionList: region.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Region);
